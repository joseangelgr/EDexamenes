package dataStructures.dictionary;
import dataStructures.list.List;

import dataStructures.list.ArrayList;
import dataStructures.set.AVLSet;
import dataStructures.set.Set;
import dataStructures.tuple.Tuple2;

/**
 * Estructuras de Datos. Grados en Informatica. UMA.
 * Examen de septiembre de 2018.
 *
 * Apellidos, Nombre: ALEJANDRO CALVO CORRALIZA
 * ESTUDIO FEBRERO
  */
public class HashBiDictionary<K,V> implements BiDictionary<K,V>{
	private Dictionary<K,V> bKeys;
	private Dictionary<V,K> bValues;
	
	public HashBiDictionary() {
		// TODO
		bKeys = new HashDictionary<>();
		bValues = new HashDictionary<>();
	}
	
	public boolean isEmpty() {
		// TODO
		boolean res = false;
		if(bKeys.isEmpty()){
			res = true;
		}
		return res;
	}
	
	public int size() {
		// TODO
		return bKeys.size();
	}
	
	public void insert(K k, V v) {
		// TODO
		boolean found = false;//todo esto primero mucho mejor con bkeys.isDefinedAt(k)
		for(K ks : bKeys.keys()){
			if(ks.equals(k)) found = true;
		}
		if(found){
			bKeys.delete(k);
			bValues.delete(v);
			bKeys.insert(k,v);
			bValues.insert(v,k);
		}else{
			bKeys.insert(k,v);
			bValues.insert(v,k);
		}
	}
	
	public V valueOf(K k) {
		// TODO
		V res = null;
		if(bKeys.isDefinedAt(k)){
			res = bKeys.valueOf(k);
		}
		return res;
	}
	
	public K keyOf(V v) {
		// TODO
		K res = null;
		if(bValues.isDefinedAt(v)){
			res = bValues.valueOf(v);
		}
		return res;
	}
	
	public boolean isDefinedKeyAt(K k) {
		return bKeys.isDefinedAt(k);
	}
	
	public boolean isDefinedValueAt(V v) {
		return bValues.isDefinedAt(v);
	}
	
	public void deleteByKey(K k) {
		// TODO
		if(isDefinedKeyAt(k)){
			bKeys.delete(k);
			bValues.delete(valueOf(k));
		}
	}
	
	public void deleteByValue(V v) {
		// TODO
		if(isDefinedValueAt(v)){
			bValues.delete(v);
			bKeys.delete(keyOf(v));
		}
	}
	
	public Iterable<K> keys() {
		return bKeys.keys();
	}
	
	public Iterable<V> values() {
		return bValues.keys();
	}
	
	public Iterable<Tuple2<K, V>> keysValues() {
		return bKeys.keysValues();
	}
	
		
	public static <K,V extends Comparable<? super V>> BiDictionary<K, V> toBiDictionary(Dictionary<K,V> dict) {
		// TODO
		BiDictionary<K, V> res = new HashBiDictionary<>();
		for(Tuple2<K,V> t : dict.keysValues()) {
			res.insert(t._1(),t._2());
		}
		if(!esInyectivo(res)){//creo que habia que comprobar que no fuera inyectivo el dictionary dado...en fin
			throw new IllegalArgumentException("diccionario no inyectivo");
		}
		return res;
	}

	private static <V extends Comparable<? super V>, K> boolean esInyectivo(BiDictionary<K,V> bidi) {
		boolean res = true;
		for(K keys : bidi.keys()){
			if(!bidi.isDefinedKeyAt(keys)){
				res = false;
			}
		}
		for(V values : bidi.values()){
			if(!bidi.isDefinedValueAt(values)){
				res = false;
			}
		}
		return res;
	}
	
	public <W> BiDictionary<K, W> compose(BiDictionary<V,W> bdic) {
		// TODO
		BiDictionary<K, W> res = new HashBiDictionary<>();
		for(Tuple2<K,V> t : this.keysValues()){
			if(bdic.isDefinedKeyAt(valueOf(t._1()))){
				res.insert(t._1(),bdic.valueOf(t._2()));
			}
		}
		return res;
	}
		
	public static <K extends Comparable<? super K>> boolean isPermutation(BiDictionary<K,K> bd) {
		// TODO
		boolean res = true;
		for(K k: bd.keys()){
			if(!bd.isDefinedValueAt(k)){
				res = false;
			}
		}
		return res;
	}
	
	// Solo alumnos con evaluaci�n por examen final.
    // =====================================
	
	public static <K extends Comparable<? super K>> List<K> orbitOf(K k, BiDictionary<K,K> bd) {
		// TODO
		return null;
	}
	
	public static <K extends Comparable<? super K>> List<List<K>> cyclesOf(BiDictionary<K,K> bd) {
		// TODO
		return null;
	}

    // =====================================
	
	
	@Override
	public String toString() {
		return "HashBiDictionary [bKeys=" + bKeys + ", bValues=" + bValues + "]";
	}
	
	
}
