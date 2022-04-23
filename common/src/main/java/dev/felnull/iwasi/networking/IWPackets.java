package dev.felnull.iwasi.networking;

import dev.architectury.networking.NetworkManager;
import dev.felnull.iwasi.IWasi;
import dev.felnull.iwasi.client.handler.ClientMessageHandler;
import dev.felnull.iwasi.data.ContinuousActionData;
import dev.felnull.iwasi.gun.trans.player.GunPlayerTrans;
import dev.felnull.iwasi.gun.trans.GunTransRegistry;
import dev.felnull.iwasi.server.handler.ServerMessageHandler;
import dev.felnull.otyacraftengine.networking.PacketMessage;
import io.netty.buffer.Unpooled;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public class IWPackets {
    public static final ResourceLocation CONTINUOUS_ACTION_INPUT = new ResourceLocation(IWasi.MODID, "continuous_action_input");
    public static final ResourceLocation ACTION_INPUT = new ResourceLocation(IWasi.MODID, "action_input");
    public static final ResourceLocation TMP_HAND_ITEMS_SYNC = new ResourceLocation(IWasi.MODID, "tmp_hand_items_sync");

    public static void init() {
        NetworkManager.registerReceiver(NetworkManager.c2s(), CONTINUOUS_ACTION_INPUT, (friendlyByteBuf, packetContext) -> ServerMessageHandler.onContinuousActionInputMessage(new ContinuousActionInputMessage(friendlyByteBuf), packetContext));
        NetworkManager.registerReceiver(NetworkManager.c2s(), ACTION_INPUT, (friendlyByteBuf, packetContext) -> ServerMessageHandler.onActionInputMessage(new ActionInputMessage(friendlyByteBuf), packetContext));
    }

    public static void clientInit() {
        NetworkManager.registerReceiver(NetworkManager.s2c(), TMP_HAND_ITEMS_SYNC, (friendlyByteBuf, packetContext) -> ClientMessageHandler.onTmpHandItemsSyncMessage(new TmpHandItemsSyncMessage(friendlyByteBuf), packetContext));
    }

    public static class TmpHandItemsSyncMessage implements PacketMessage {
        public final UUID playerId;
        public final InteractionHand hand;
        public final NonNullList<ItemStack> items;

        public TmpHandItemsSyncMessage(FriendlyByteBuf buf) {
            this.playerId = buf.readUUID();
            this.hand = buf.readEnum(InteractionHand.class);
            items = NonNullList.withSize(buf.readInt(), ItemStack.EMPTY);
            items.replaceAll(ignored -> buf.readItem());
        }

        public TmpHandItemsSyncMessage(UUID playerId, InteractionHand hand, NonNullList<ItemStack> items) {
            this.playerId = playerId;
            this.hand = hand;
            this.items = items;
        }

        @Override
        public FriendlyByteBuf toFBB() {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeUUID(playerId);
            buf.writeEnum(hand);
            buf.writeInt(items.size());
            for (ItemStack item : items) {
                buf.writeItem(item);
            }
            return buf;
        }
    }

    public static class GunTransResetMessage implements PacketMessage {
        public final InteractionHand hand;
        public final GunPlayerTrans gunTrans;

        public GunTransResetMessage(FriendlyByteBuf buf) {
            this.hand = buf.readEnum(InteractionHand.class);
            this.gunTrans = GunTransRegistry.getById(buf.readInt());
        }

        public GunTransResetMessage(InteractionHand hand, GunPlayerTrans gunTrans) {
            this.hand = hand;
            this.gunTrans = gunTrans;
        }

        @Override
        public FriendlyByteBuf toFBB() {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeEnum(hand);
            buf.writeInt(GunTransRegistry.getId(gunTrans));
            return buf;
        }
    }

    public static class ActionInputMessage implements PacketMessage {
        public final ActionType action;

        public ActionInputMessage(FriendlyByteBuf buf) {
            this.action = buf.readEnum(ActionType.class);
        }

        public ActionInputMessage(ActionType action) {
            this.action = action;
        }

        @Override
        public FriendlyByteBuf toFBB() {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            buf.writeEnum(action);
            return buf;
        }

        public static enum ActionType {
            RELOAD,TRIGGER
        }
    }

    public static class ContinuousActionInputMessage implements PacketMessage {
        public final ContinuousActionData data;

        public ContinuousActionInputMessage(FriendlyByteBuf buf) {
            this.data = ContinuousActionData.read(buf);
        }

        public ContinuousActionInputMessage(ContinuousActionData data) {
            this.data = data;
        }

        @Override
        public FriendlyByteBuf toFBB() {
            FriendlyByteBuf buf = new FriendlyByteBuf(Unpooled.buffer());
            data.write(buf);
            return buf;
        }
    }
}
