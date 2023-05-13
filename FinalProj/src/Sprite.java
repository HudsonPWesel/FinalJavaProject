public class Sprite {
    public String spritesheetFileName;
    public int width;
    public int height;
    public String[] animationCycleRowNames;
    public int[] spriteColumnSequence;

    public Sprite(String spritesheetFileName, int[] dimensions, String[] animationCycleRowNames,
            int[] spriteColumnSequence) {
        this.spritesheetFileName = spritesheetFileName;
        this.width = dimensions[0];
        this.height = dimensions[1];
        this.animationCycleRowNames = animationCycleRowNames;
        this.spriteColumnSequence = spriteColumnSequence;
    }

}
