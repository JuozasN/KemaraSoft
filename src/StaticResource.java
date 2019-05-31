import java.util.Arrays;

public class StaticResource extends Resource {
    private Block[] elementList;

    public StaticResource (){
        super();
    }

    public void create(OS os, Process creator, Title title, int eListSize){
        this.elementList = new Block[eListSize];
        for(int i = 0; i < eListSize; i++){
            elementList[i] = new Block();
        }
        super.create(os, creator, title);
    }

    public Block[] getElementList() {
        return this.elementList;
    }

    public void delete() {
        this.clearElementList();
        super.delete();
    }

    public Block[] getElements(int size){
        Block[] blocks = new Block[size];
        int i = 0;
        for(int j = 0; j < Utils.KM_BLOCK_COUNT; j++){
            if(elementList[j].isEmpty()){
                blocks[i] = elementList[j];
                if(i == 0) blocks[i].setWord(0, j);
                ++i;
            }
            if(i == size) break;
        }

        return blocks;
    }

    public Block getElement(int index){
        return elementList[index];
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
