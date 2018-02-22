package hu.bme.aut.android.tictactoe.ui;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PointF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import hu.bme.aut.android.tictactoe.MainActivity;
import hu.bme.aut.android.tictactoe.R;
import hu.bme.aut.android.tictactoe.model.TicTacToeModel;

public class TicTacToeView extends View {

    private Paint paintBackground;
    private Paint paintLine;
    private Paint paintFont;


    private PointF tmpPlayer = null;

    private Bitmap bitmapBg = null;

    public TicTacToeView(Context context,
                         @Nullable AttributeSet attrs) {
        super(context, attrs);

        paintBackground = new Paint();
        paintBackground.setColor(Color.BLACK);
        paintBackground.setStyle(Paint.Style.FILL);

        paintLine = new Paint();
        paintLine.setColor(Color.WHITE);
        paintLine.setStyle(Paint.Style.STROKE);
        paintLine.setStrokeWidth(5);

        paintFont = new Paint();
        paintFont.setColor(Color.RED);
        paintFont.setTextSize(60);

        bitmapBg = BitmapFactory.decodeResource(getResources(),
                R.drawable.background);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        paintFont.setTextSize(getHeight() / 3);

        bitmapBg = Bitmap.createScaledBitmap(bitmapBg,
                getWidth(), getHeight(), false);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawRect(0,
                0, getWidth(), getHeight(),
                paintBackground);

        canvas.drawBitmap(bitmapBg, 0, 0, null);

        drawGameArea(canvas);

        drawPlayers(canvas);

        drawTmpPlayer(canvas);

        canvas.drawText("5", 100, getHeight()/3, paintFont);

    }

    private void drawTmpPlayer(Canvas canvas) {
        if (tmpPlayer != null) {
            if (TicTacToeModel.getInstance().getNextPlayer()
                    == TicTacToeModel.CIRCLE) {
                canvas.drawCircle(tmpPlayer.x, tmpPlayer.y, getHeight() / 6 - 2,
                        paintLine);
            } else {
                canvas.drawLine(tmpPlayer.x - getWidth() / 6,
                        tmpPlayer.y - getHeight() / 6,
                        tmpPlayer.x + getWidth() / 6,
                        tmpPlayer.y + getHeight() / 6, paintLine);

                canvas.drawLine(tmpPlayer.x - getWidth() / 6,
                        tmpPlayer.y + getHeight() / 6,
                        tmpPlayer.x + getWidth() / 6,
                        tmpPlayer.y - getHeight() / 6, paintLine);
            }
        }
    }

    private void drawPlayers(Canvas canvas) {
        for (short i = 0; i < 3; i++) {
            for (short j = 0; j < 3; j++) {
                if (TicTacToeModel.getInstance().getFieldContent(i,j) == TicTacToeModel.CIRCLE) {

                    // draw a circle at the center of the field

                    // X coordinate: left side of the square + half width of the square
                    float centerX = i * getWidth() / 3 + getWidth() / 6;
                    float centerY = j * getHeight() / 3 + getHeight() / 6;
                    int radius = getHeight() / 6 - 2;

                    canvas.drawCircle(centerX, centerY, radius, paintLine);

                } else if (TicTacToeModel.getInstance().getFieldContent(i,j) == TicTacToeModel.CROSS) {
                    canvas.drawLine(i * getWidth() / 3, j * getHeight() / 3,
                            (i + 1) * getWidth() / 3,
                            (j + 1) * getHeight() / 3, paintLine);

                    canvas.drawLine((i + 1) * getWidth() / 3, j * getHeight() / 3,
                            i * getWidth() / 3, (j + 1) * getHeight() / 3, paintLine);
                }
            }
        }
    }

    private void drawGameArea(Canvas canvas) {


        canvas.drawRect(0, 0, getWidth(), getHeight(), paintLine);
        // two horizontal lines
        canvas.drawLine(0, getHeight() / 3, getWidth(),
                getHeight() / 3,
                paintLine);
        canvas.drawLine(0, 2 * getHeight() / 3, getWidth(),
                2 * getHeight() / 3, paintLine);

        // two vertical lines
        canvas.drawLine(getWidth() / 3, 0,
                getWidth() / 3,
                getHeight(),
                paintLine);
        canvas.drawLine(2 * getWidth() / 3, 0,
                2 * getWidth() / 3,
                getHeight(),
                paintLine);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            tmpPlayer = new PointF(event.getX(), event.getY());
            invalidate();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            tmpPlayer = null;

            int tX = ((int)event.getX() / (getWidth() / 3));
            int tY = ((int)event.getY() / (getHeight() / 3));

            if (TicTacToeModel.getInstance().getFieldContent((short)tX, (short)tY)
                    == TicTacToeModel.EMPTY) {
                TicTacToeModel.getInstance().setFieldContent(
                        (short) tX,
                        (short) tY,
                        TicTacToeModel.getInstance().getNextPlayer());
                TicTacToeModel.getInstance().changeNextPlayer();

                ((MainActivity)getContext()).showMessage(getContext().getString(R.string.text_next));
            }

            invalidate();
        }


        return true;
    }


    public void clearBoard() {
        TicTacToeModel.getInstance().resetGame();
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = MeasureSpec.getSize(widthMeasureSpec);
        int h = MeasureSpec.getSize(heightMeasureSpec);
        int d = w == 0 ? h : h == 0 ? w : w < h ? w : h;
        setMeasuredDimension(d, d);
    }
}
