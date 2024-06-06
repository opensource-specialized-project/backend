package com.medikok.backend.shared;

public class Vertex {
    private float x;
    private float y;

    // 생성자
    public Vertex(float x, float y) {
        this.x = x;
        this.y = y;
    }

    // Getter 및 Setter 메서드
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "Vertex{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
