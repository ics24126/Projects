public class PixelComparisonThingy {

    public int comparePixels(int rgb1, int rgb2){
        int r1,g1,b1,r2,g2,b2;

        r1=(rgb1>>16)&0xff;
        g1=(rgb1>>8)&0xff;
        b1=(rgb1)&0xff;

        r2=(rgb2>>16)&0xff;
        g2=(rgb2>>8)&0xff;
        b2=(rgb2)&0xff;

        int redDiff = Math.abs(r1-r2);
        int greenDiff = Math.abs(g1-g2);
        int blueDiff = Math.abs(b1-b2);


        return redDiff+greenDiff+blueDiff;
    }
}
