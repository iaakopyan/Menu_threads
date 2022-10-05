import javax.swing.*;
public class Frame extends JFrame {
    public Frame(int width, int height) {
        super("����������� ��� ���� �� ������� ���� �������");
        this.setResizable(false);

        this.pack();
        this.setSize(width+getInsets().left+getInsets().right, height+getInsets().top+getInsets().bottom );
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(null);
        this.setLocationRelativeTo(null); //���� � ���������
        this.add(new Game(this));

        this.setVisible(true);
    }
}