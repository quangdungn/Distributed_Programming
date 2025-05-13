import java.util.ArrayList;
import java.util.List;

public class BufferMonitor {
    private final List<Integer> buffer; 
    private final int capacity;       
    public BufferMonitor(int capacity) {
        this.capacity = capacity;
        this.buffer = new ArrayList<>();
    }

    public synchronized void put(int value) throws InterruptedException {
        while (buffer.size() == capacity) {
            wait();
        }
        buffer.add(value);    
        notifyAll();          
    }

    public synchronized int get() throws InterruptedException {
        while (buffer.isEmpty()) {
            wait();
        }
        int value = buffer.remove(0);  
        notifyAll();                  
        return value;
    }
}