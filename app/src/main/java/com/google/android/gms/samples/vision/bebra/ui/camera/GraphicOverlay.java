
package com.google.android.gms.samples.vision.bebra.ui.camera;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.google.android.gms.vision.CameraSource;

import java.util.HashSet;
import java.util.Set;

public class GraphicOverlay<T extends GraphicOverlay.Graphic> extends View {
    private final Object lock = new Object();
    private int previewWidth;
    private float widthScaleFactor = 1.0f;
    private int previewHeight;
    private float heightScaleFactor = 1.0f;
    private int facing = CameraSource.CAMERA_FACING_BACK;
    private Set<T> graphic = new HashSet<>();

    public static abstract class Graphic {
        private GraphicOverlay mOverlay;

        public Graphic(GraphicOverlay overlay) {
            mOverlay = overlay;
        }

        public abstract void draw(Canvas canvas);

        public abstract boolean contains(float x, float y);


        public float scaleX(float horizontal) {
            return horizontal * mOverlay.widthScaleFactor;
        }

        public float scaleY(float vertical) {
            return vertical * mOverlay.heightScaleFactor;
        }

        public float translateX(float x) {
            if (mOverlay.facing == CameraSource.CAMERA_FACING_FRONT) {
                return mOverlay.getWidth() - scaleX(x);
            } else {
                return scaleX(x);
            }
        }


        public float translateY(float y) {
            return scaleY(y);
        }


        public RectF translateRect(RectF inputRect) {
            RectF returnRect = new RectF();

            returnRect.left = translateX(inputRect.left);
            returnRect.top = translateY(inputRect.top);
            returnRect.right = translateX(inputRect.right);
            returnRect.bottom = translateY(inputRect.bottom);

            return returnRect;
        }

        public void postInvalidate() {
            mOverlay.postInvalidate();
        }
    }

    public GraphicOverlay(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void clear() {
        synchronized (lock) {
            graphic.clear();
        }
        postInvalidate();
    }

    public void add(T graphic) {
        synchronized (lock) {
            this.graphic.add(graphic);
        }
        postInvalidate();
    }

    public void remove(T graphic) {
        synchronized (lock) {
            this.graphic.remove(graphic);
        }
        postInvalidate();
    }

    public T getGraphicAtLocation(float rawX, float rawY) {
        synchronized (lock) {
            int[] location = new int[2];
            this.getLocationOnScreen(location);
            for (T graphic : graphic) {
                if (graphic.contains(rawX - location[0], rawY - location[1])) {
                    return graphic;
                }
            }
            return null;
        }
    }

    public void setCameraInfo(int previewWidth, int previewHeight, int facing) {
        synchronized (lock) {
            this.previewWidth = previewWidth;
            this.previewHeight = previewHeight;
            this.facing = facing;
        }
        postInvalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        synchronized (lock) {
            if ((previewWidth != 0) && (previewHeight != 0)) {
                widthScaleFactor = (float) canvas.getWidth() / (float) previewWidth;
                heightScaleFactor = (float) canvas.getHeight() / (float) previewHeight;
            }

            for (Graphic graphic : graphic) {
                graphic.draw(canvas);
            }
        }
    }
}
