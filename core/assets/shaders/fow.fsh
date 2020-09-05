varying vec2 v_texCoord0;
varying vec4 v_color;

uniform sampler2D u_sampler2D;
uniform float positions[400];
uniform float size;
uniform float resolution[2];
void main(void) {
	vec4 oriColor = texture2D(u_sampler2D, v_texCoord0) * v_color;
	vec4 temp = texture2D(u_sampler2D, v_texCoord0) * v_color;
	float smoothing = 1;
	if(positions[0] != -1){
		vec2 pixelPos = (gl_FragCoord.xy / vec2(resolution[0], resolution[0]))*size - vec2(positions[0],positions[1]);
		float pixelLen = length(pixelPos);
		smoothing = smoothstep(1,0.5,pixelLen);
		for(int i = 2; i < positions.length-1; i+=2){
			if(positions[i] != -1){
				pixelPos = (gl_FragCoord.xy / vec2(resolution[0], resolution[0]))*size - vec2(positions[i],positions[i+1]);
				pixelLen = length(pixelPos);
				smoothing = smoothing + smoothstep(1,0.5,pixelLen);
			}
		}
	}
	temp.rgb = mix(temp.rgb,+temp.rgb*smoothing,1);
	if(temp.r > oriColor.r || temp.g > oriColor.g || temp.b > oriColor.b)temp.rgb = oriColor.rgb;
	gl_FragColor = temp;
}
