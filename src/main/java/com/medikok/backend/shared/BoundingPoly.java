package com.medikok.backend.shared;

import java.util.List;

public class BoundingPoly {
    private List<Vertex> vertices;

    // 생성자
    public BoundingPoly(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    // Getter 및 Setter 메서드
    public List<Vertex> getVertices() {
        return vertices;
    }

    public void setVertices(List<Vertex> vertices) {
        this.vertices = vertices;
    }

    @Override
    public String toString() {
        return "BoundingPoly{" +
                "vertices=" + vertices +
                '}';
    }

    public float getMinX() {
        return 0;
    }
}
