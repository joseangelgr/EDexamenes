
/**
 * Huffman trees and codes.
 *
 * Data Structures, Grado en Informatica. UMA.
 *
 *
 * Student's name:
 * Student's group:
 */

import dataStructures.dictionary.AVLDictionary;
import dataStructures.dictionary.Dictionary;
import dataStructures.list.LinkedList;
import dataStructures.list.List;
import dataStructures.priorityQueue.BinaryHeapPriorityQueue;
import dataStructures.priorityQueue.PriorityQueue;
import dataStructures.tuple.Tuple2;
import huffman.WLeafTree;

import java.io.CharConversionException;
import java.util.HashSet;

public class Huffman {

    // Exercise 1
    public static Dictionary<Character, Integer> weights(String s) {
    	//to do
        Dictionary<Character,Integer> res = new AVLDictionary<>();
        HashSet<Character> bag = new HashSet<>();

        for(Character c: s.toCharArray()){

            if(bag.contains(c)){

                res.insert(c,res.valueOf(c)+1);

            }

            else {

                res.insert(c,1);
                bag.add(c);
            }

        }
        return res;
    }

    // Exercise 2.a
    public static PriorityQueue<WLeafTree<Character>> huffmanLeaves(String s) {
    	//to do
        PriorityQueue <WLeafTree<Character>> res = new BinaryHeapPriorityQueue<>();
        Dictionary<Character,Integer> dic = weights(s);

        for (Tuple2<Character,Integer> t: weights(s).keysValues()){

            //los añadimos a la cola de prioridad
            res.enqueue(new WLeafTree<>(t._1(),t._2()));

        }

    	        return res;
    }

    // Exercise 2.b
    public static WLeafTree<Character> huffmanTree(String s) {
        int num = 0;

        HashSet<Character> bag = new HashSet<>();

        for(Character c :s.toCharArray()){

            if(!bag.contains(c)){

                num++;
                bag.add(c);

            }

        }

        if(num < 2){
            throw new HuffmanException("menos de 2 caracteres diferentes");
        }

        WLeafTree<Character> res,t1,t2;
        PriorityQueue<WLeafTree<Character>> cola = huffmanLeaves(s);

        t1 = cola.first();
        cola.dequeue();
        t2 = cola.first();

        res = new WLeafTree<>(t1,t2);

        while(!cola.isEmpty()){



            res = new WLeafTree<>(res,cola.first());
            cola.dequeue();



        }

    	//to do 
    	return res;
    }

    // Exercise 3.a
    public static Dictionary<Character, List<Integer>> joinDics(Dictionary<Character, List<Integer>> d1, Dictionary<Character, List<Integer>> d2) {
        //to do
        Dictionary <Character,List<Integer>> res = new AVLDictionary<>();
        for(Tuple2<Character,List<Integer>> t: d1.keysValues()){

            res.insert(t._1(),t._2());

        }
        for(Tuple2<Character,List<Integer>> t: d2.keysValues()){

            res.insert(t._1(),t._2());

        }
    	return res;
    }

    // Exercise 3.b
    public static Dictionary<Character, List<Integer>> prefixWith(int i, Dictionary<Character, List<Integer>> d) {
        //to do
        Dictionary<Character,List<Integer>> dic = new AVLDictionary<>();

        for (Tuple2<Character,List<Integer>> t :d.keysValues()){
            t._2().prepend(i);
            dic.insert(t._1(),t._2());
        }

    	return dic;
    }

    // Exercise 3.c
    public static Dictionary<Character, List<Integer>> huffmanCode(WLeafTree<Character> ht) {
        //to do
        Dictionary<Character,List<Integer>> res = new AVLDictionary<>();
        if(ht.isLeaf()){
            res.insert(ht.elem(),new LinkedList<>());

        }

        else {

            return joinDics(prefixWith(0,huffmanCode(ht.leftChild())),prefixWith(1,huffmanCode(ht.rightChild())));

        }
    	return res;
    }

    // Exercise 4
    public static List<Integer> encode(String s, Dictionary<Character, List<Integer>> hc) {
        //to do
        List<Integer> res = new LinkedList<>();
        List<Integer> listAux;
        for(char c: s.toCharArray()){
            listAux = hc.valueOf(c);
            for(int i : listAux){
                res.append(i);
            }
        }
        return res;
    }

    // Exercise 5
    public static String decode(List<Integer> bits, WLeafTree<Character> ht) {
        //to do

        WLeafTree<Character> aux = ht;
        String res = "";
        for(int i : bits){
            if(i == 0){
                aux = aux.leftChild();
            }else{
                aux = aux.rightChild();
            }
            if(aux.isLeaf()){
                res += aux.elem();
                aux = ht;
            }
        }
        return res;

    }
}
