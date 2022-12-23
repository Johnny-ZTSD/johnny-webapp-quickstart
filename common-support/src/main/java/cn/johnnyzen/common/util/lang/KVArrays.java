package cn.seres.bd.dataservice.common.utils;

/**
 * @author 408675 (tai.zeng@seres.cn)
 * @version v1.0
 * @project bdp-data-service-parent
 * @create-time 2022/10/3 16:01
 * @description ...
 */
public class KVArrays <K, V> {
    private K[] keyArray;

    private V[] valueArray;

    public KVArrays() {

    }

    public KVArrays(K[] keyArray, V[] valueArray) {
        this.keyArray = keyArray;
        this.valueArray = valueArray;
    }

    public K[] getKeyArray() {
        return keyArray;
    }

    public void setKeyArray(K[] keyArray) {
        this.keyArray = keyArray;
    }

    public V[] getValueArray() {
        return valueArray;
    }

    public void setValueArray(V[] valueArray) {
        this.valueArray = valueArray;
    }
}
