package model.entity;

public interface Interface {

    // Position (ตำแหน่งตัวละคร)
    abstract public int getX();
    abstract public int getY();

    // Movement (การเคลื่อนไหว)
    abstract public void moveX();
    abstract public void moveY();

    // State checker (ตรวจสอบสถานะต่างๆ)
    abstract public void collided(Interface entity) throws InterruptedException; // ถูกชน
    abstract public void respawn(); // วางตัวละครบน map ใหม่
    abstract public void respawn(int x, int y); // วางตัวละครบน map ใหม่แบบกำหนดพิกัดเอง
    abstract public void stop(); // หยุดเคลื่อนไหว

    // Image render
    abstract public void repaint(); // วาดตัวละครลง pane
}
