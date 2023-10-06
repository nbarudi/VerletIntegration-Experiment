package ca.bungo.physics.verletintegrationsym.math;

import javafx.scene.paint.Color;

public class Colors {

    public static Color getRainbow(float time){
        float r = (float) Math.abs(Math.sin(time));
        float g = (float) Math.abs(Math.sin(time + 0.33f * 2.0f *Math.PI));
        float b = (float) Math.abs(Math.sin(time + 0.66f * 2.0f *Math.PI));
        return Color.color(r,g,b);
    }
}
