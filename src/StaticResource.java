import java.util.Arrays;

public class StaticResource extends Resource {
    private Block[] elementList;

    public StaticResource (){
        super();
    }

    public void create(OS os, Process creator, Title title, int eListSize){
        this.elementList = new Block[eListSize];
        super.create(os, creator, title);
    }

    public Block[] getElementList() {
        return this.elementList;
    }

    public void delete() {
        this.clearElementList();
        super.delete();
        //"naikinamas pats aprasas"...
    }

    public void clearElementList() {
        Arrays.fill(this.elementList, null);
    }

    public void release(Block element) {
        // perduodame elementą resursui ir kviečiame paskirstytoją,
        // kad jis perduotų elementą jo laukiančiam procesui
        this.addLast(element);
        super.release();
    }

    public void addLast(Block element){
        for (int i = 0; i < this.elementList.length; i++) {
            if (this.elementList[i] == null){
                this.elementList[i] = element;
                break;
            }
        }
    }
}
