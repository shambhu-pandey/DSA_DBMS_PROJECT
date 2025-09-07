package com.example.ambulancedispatch.service;

import com.example.ambulancedispatch.model.Ambulance;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class MinHeapPriorityQueue {
    private List<AmbulanceDistance> heap;

    public MinHeapPriorityQueue() {
        this.heap = new ArrayList<>();
    }

    private static class AmbulanceDistance {
        Ambulance ambulance;
        int distance;

        AmbulanceDistance(Ambulance ambulance, int distance) {
            this.ambulance = ambulance;
            this.distance = distance;
        }
    }

    private void swap(int i, int j) {
        AmbulanceDistance temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }

    private void heapifyUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;
            if (heap.get(index).distance < heap.get(parent).distance) {
                swap(index, parent);
                index = parent;
            } else {
                break;
            }
        }
    }

    private void heapifyDown(int index) {
        int size = heap.size();
        while (true) {
            int left = 2 * index + 1;
            int right = 2 * index + 2;
            int smallest = index;

            if (left < size && heap.get(left).distance < heap.get(smallest).distance) {
                smallest = left;
            }
            if (right < size && heap.get(right).distance < heap.get(smallest).distance) {
                smallest = right;
            }
            if (smallest != index) {
                swap(index, smallest);
                index = smallest;
            } else {
                break;
            }
        }
    }

    public void add(Ambulance ambulance, int distance) {
        AmbulanceDistance ad = new AmbulanceDistance(ambulance, distance);
        heap.add(ad);
        heapifyUp(heap.size() - 1);
    }

    public Ambulance poll() {
        if (heap.isEmpty()) return null;
        AmbulanceDistance root = heap.get(0);
        AmbulanceDistance last = heap.remove(heap.size() - 1);
        if (!heap.isEmpty()) {
            heap.set(0, last);
            heapifyDown(0);
        }
        return root.ambulance;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }
}
