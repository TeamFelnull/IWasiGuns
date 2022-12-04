package dev.felnull.iwasi.client.model.pointer;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.math.Vector3f;

public record ModelPointer(Vector3f size, Vector3f position, Vector3f origin, Vector3f rotation) {
    private static final int VERSION = 0;

    public static ModelPointer parse(JsonObject jo) {
        int version = jo.get("version").getAsInt();
        if (version != VERSION)
            throw new RuntimeException("Unsupported version");

        JsonArray sizeJa = jo.getAsJsonArray("size");
        JsonArray positionJa = jo.getAsJsonArray("position");
        JsonArray originJa = jo.getAsJsonArray("origin");
        JsonArray rotationJa = jo.getAsJsonArray("rotation");

        Vector3f size = new Vector3f(sizeJa.get(0).getAsFloat(), sizeJa.get(1).getAsFloat(), sizeJa.get(2).getAsFloat());
        Vector3f position = new Vector3f(positionJa.get(0).getAsFloat(), positionJa.get(1).getAsFloat(), positionJa.get(2).getAsFloat());
        Vector3f origin = new Vector3f(originJa.get(0).getAsFloat(), originJa.get(1).getAsFloat(), originJa.get(2).getAsFloat());
        Vector3f rotation = new Vector3f(rotationJa.get(0).getAsFloat(), rotationJa.get(1).getAsFloat(), rotationJa.get(2).getAsFloat());

        return new ModelPointer(size, position, origin, rotation);
    }

    public JsonObject toJson() {
        JsonObject jo = new JsonObject();
        jo.addProperty("version", VERSION);

        JsonArray sizeJa = new JsonArray();
        JsonArray positionJa = new JsonArray();
        JsonArray originJa = new JsonArray();
        JsonArray rotationJa = new JsonArray();

        sizeJa.add(size.x());
        sizeJa.add(size.y());
        sizeJa.add(size.z());

        originJa.add(origin.x());
        originJa.add(origin.y());
        originJa.add(origin.z());

        positionJa.add(position.x());
        positionJa.add(position.y());
        positionJa.add(position.z());

        rotationJa.add(rotation.x());
        rotationJa.add(rotation.y());
        rotationJa.add(rotation.z());

        jo.add("size", sizeJa);
        jo.add("position", positionJa);
        jo.add("origin", originJa);
        jo.add("rotation", rotationJa);
        return jo;
    }
}
