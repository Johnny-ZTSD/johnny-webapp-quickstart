package cn.johnnyzen;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/5/24  22:05:22
 * @description: ...
 */

public class ListSortTest {
    public static void main(String[] args) {
        ArrayList<ArrayList<Integer>> list = new ArrayList();

        Integer [][] array = {
                {-1,2,3}, {-1, 3,2},
                {2,-1, 3}, {2,3,-1},
                {3,2,-1}, {3,-1,2}
        };

        list.add( new ArrayList(Arrays.asList(array[0])) );
        list.add( new ArrayList(Arrays.asList(array[1])) );
        list.add( new ArrayList(Arrays.asList(array[2])) );
        list.add( new ArrayList(Arrays.asList(array[3])) );
        list.add( new ArrayList(Arrays.asList(array[4])) );
        list.add( new ArrayList(Arrays.asList(array[5])) );

        list.sort(new Comparator<ArrayList<Integer>>() {
            @Override
            public int compare(ArrayList<Integer> o1, ArrayList<Integer> o2) {
                ArrayList<Integer> list1 = (ArrayList<Integer>) o1;
                ArrayList<Integer> list2 = (ArrayList<Integer>) o2;
                if(list1.size() != list2.size()){
                    throw new RuntimeException("length error");
                }
                int length = list1.size();
                for(int i=0;i<length;i++){
                    Integer element1 = list1.get(i);
                    Integer element2 = list2.get(i);
                    if(element1 != element2){
                        return element1 - element2;//升序
                    }
                }
                return 0;//2个 list 的内容 相等
            }
        });
        for(int i=0;i<list.size();i++){
            Print.print(list.get(i)," ");
        }
    }
}
