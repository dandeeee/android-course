package com.dandeeee.tictactoe.ui.component;

import com.dandeeee.tictactoe.model.GameLogic;
import com.dandeeee.tictactoe.R;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;


public class BoardView extends View {

    // Width of the board grid lines
    public static final int GRID_WIDTH = 6;

    // We need a reference to the game so we can draw ourself properly
    private GameLogic mGame;

    private Bitmap mHumanBitmap;
    private Bitmap mComputerBitmap;

    private Paint mPaint;

    private int mBoardColor = Color.LTGRAY;

    public BoardView(Context context) {
        super(context);
        initialize();
    }

    public BoardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initialize();
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initialize();
    }

    public void initialize() {
        mHumanBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.x_img);
        mComputerBitmap = BitmapFactory.decodeResource(getResources(),
                R.drawable.o_img);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setGame(GameLogic game) {
        mGame = game;
    }

    public int getBoardCellWidth() {
        return getWidth() / 3;
    }

    public int getBoardCellHeight() {
        return getHeight() / 3;
    }

    public void setBoardColor(int boardColor) {
        mBoardColor = boardColor;
    }

    public int getBoardColor() {
        return mBoardColor;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Determine the width and height of the View
        int boardWidth = getWidth();
        int boardHeight = getHeight();

        // Make thick, light gray lines
        mPaint.setColor(mBoardColor);
        mPaint.setStrokeWidth(GRID_WIDTH);

        // Draw the board lines
        int cellWidth = getBoardCellWidth();
        canvas.drawLine(cellWidth, 0, cellWidth, boardHeight, mPaint);
        canvas.drawLine(cellWidth * 2, 0, cellWidth * 2, boardHeight, mPaint);
        int cellHeight = getBoardCellHeight();
        canvas.drawLine(0, cellHeight, boardWidth, cellHeight, mPaint);
        canvas.drawLine(0, cellHeight * 2, boardWidth, cellHeight * 2, mPaint);

        // Draw all the X and O images
        for (int i = 0; i < GameLogic.BOARD_SIZE; i++) {
            int col = i % 3;
            int row = i / 3;

            // Define the boundaries of a destination rectangle for the image
            int left = col * cellWidth + GRID_WIDTH;
            int top = row * cellHeight + GRID_WIDTH;
            int right = left + cellWidth - 10;
            int bottom = top + cellHeight - GRID_WIDTH - 6;

            if (mGame != null && mGame.getBoardOccupant(i) == GameLogic.HUMAN_PLAYER) {
                canvas.drawBitmap(mHumanBitmap,
                        null,  // src
                        new Rect(left, top, right, bottom),  // dest
                        null);

            } else if (mGame != null && mGame.getBoardOccupant(i) == GameLogic.COMPUTER_PLAYER) {
                canvas.drawBitmap(mComputerBitmap,
                        null,  // src
                        new Rect(left, top, right, bottom),  // dest
                        null);
            }
        }
    }

}
