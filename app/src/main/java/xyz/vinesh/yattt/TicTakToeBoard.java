package xyz.vinesh.yattt;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by vineshraju on 27/1/18.
 */

public class TicTakToeBoard extends View {
    Paint linePaint;
    float[] lines = null;
    Canvas canvas;
    float sideOfCell = 0;
    float widthOfBoard = 0;
    int currentTurn = CellValues.EX;
    int[][] board = new int[][]{
            {CellValues.EMPTY, CellValues.EMPTY, CellValues.EMPTY},
            {CellValues.EMPTY, CellValues.EMPTY, CellValues.EMPTY},
            {CellValues.EMPTY, CellValues.EMPTY, CellValues.EMPTY}
    };

    public TicTakToeBoard(Context context) {
        super(context);
        linePaint = new Paint();
        linePaint.setColor(Color.BLACK);
        linePaint.setStrokeWidth(10);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.canvas = canvas;
        drawLines(canvas);
        drawSymbols(canvas);
    }

    private void drawSymbols(Canvas canvas) {
        Paint symbolPaint = new Paint();
        symbolPaint.setTextAlign(Paint.Align.CENTER);
        symbolPaint.setTextSize(sideOfCell / 3);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String symbol = "";
                if (board[i][j] != CellValues.EMPTY) {
                    if (board[i][j] == CellValues.EX) {
                        symbolPaint.setColor(Color.RED);
                        symbol = "X";
                    } else {
                        symbolPaint.setColor(Color.BLACK);
                        symbol = "O";

                    }
                    canvas.drawText(symbol, centre(i), centre(j),
                            symbolPaint);
                }
            }
        }
    }

    private float centre(int i) {
        return (sideOfCell * i) + (sideOfCell / 2);
    }

    private void drawLines(Canvas canvas) {
        if (lines == null) {
            sideOfCell = canvas.getWidth() / 3;
            widthOfBoard = canvas.getWidth();
            lines = new float[]{sideOfCell, 0,
                    sideOfCell, widthOfBoard,
                    sideOfCell * 2, 0,
                    sideOfCell * 2, widthOfBoard,
                    0, sideOfCell,
                    widthOfBoard, sideOfCell,
                    0, sideOfCell * 2,
                    widthOfBoard, sideOfCell * 2};
        }
        canvas.drawLines(lines, linePaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        float x = event.getX();
        float y = event.getY();
        int eventAction = event.getAction();

        switch (eventAction) {
            case MotionEvent.ACTION_DOWN:
                int[] point = determineCell(x, y);
                try {
                    if (board[point[0]][point[1]] == CellValues.EMPTY) {
                        board[point[0]][point[1]] = currentTurn;
                        toggleTurn();
                        invalidateCanvas();
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    e.printStackTrace();
                }
                break;
        }
        return true;
    }

    private int[] determineCell(float x, float y) {
        int pX = (int) (x / sideOfCell);
        int pY = (int) (y / sideOfCell);
        return new int[]{pX, pY};
    }

    public void invalidateCanvas() {
        invalidate();
    }

    void toggleTurn() {
        if (currentTurn == CellValues.EX) {
            currentTurn = CellValues.OH;
            linePaint.setColor(Color.BLACK);
        } else {
            currentTurn = CellValues.EX;
            linePaint.setColor(Color.RED);
        }
    }

    static final class CellValues {
        static int EMPTY = 2, EX = 3, OH = 5;
    }
}
