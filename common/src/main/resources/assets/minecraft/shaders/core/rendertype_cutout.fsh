#version 150

#moj_import <fog.glsl>

uniform sampler2D Sampler0;

uniform vec4 ColorModulator;
uniform float FogStart;
uniform float FogEnd;
uniform vec4 FogColor;

in float vertexDistance;
in vec4 vertexColor;
in vec2 texCoord0;
in vec4 normal;
in float time;

out vec4 fragColor;
//https://qiita.com/edo_m18/items/71f6064f3355be7e4f45
void main() {
    vec4 color =texture(Sampler0, texCoord0)* vertexColor * ColorModulator;
    if (color.a < 0.1) {
        discard;//<-描画しない
    }
    float tp=abs(fract(time*1200.)-0.5)*2.;
    // color*=tp;
    fragColor = linear_fog(color, vertexDistance, FogStart, FogEnd, FogColor);
}
