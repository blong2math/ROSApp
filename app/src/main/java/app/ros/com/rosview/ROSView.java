package app.ros.com.rosview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.renderscript.Float2;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;

import app.ros.com.rosapp.R;

/*
 * Use as normal view in layout.xml
 * You should give a picture to the function setHighlight()
 * And set draw operation after onCreate(), onStart() and onResume().
 */

public class ROSView extends View {

    private Paint mPaint;
    private Bitmap highlight;
    private static float highlightWidthCompressionRate = 0.0104f;
    private static float highlightHeightCompressionRate = 0.0177f;

    private int[] tempStationIds = {102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113,
                                    114, 115, 116, 117, 119, 120, 121, 122, 123, 201, 202, 203,
                                    204, 205, 206, 207, 208, 209, 210, 211, 212, 213, 214, 215,
                                    216, 217, 218, 301, 302, 303, 304, 305, 306, 307, 308, 309,
                                    310, 311, 312, 313, 314, 315, 316, 317, 318, 319, 320, 321,
                                    322, 323, 324, 325, 326, 327, 328, 329, 330, 331, 332, 333,
                                    334, 335, 336, 337, 338, 339, 609, 610, 611, 612, 613, 614,
                                    615, 616, 617, 618, 620, 621, 623, 625, 626, 628, 632, 633};
    private float[] tempStationWidthRate = {0.8046f, 0.7026f, 0.5967f, 0.5558f, 0.4994f, 0.4446f,
                                            0.4144f, 0.3859f, 0.3580f, 0.3302f, 0.3024f, 0.2739f,
                                            0.2460f, 0.2205f, 0.2205f, 0.2205f, 0.1804f, 0.1317f,
                                            0.1070f, 0.0823f, 0.0576f, 0.7026f, 0.7026f, 0.6732f,
                                            0.6358f, 0.5966f, 0.5546f, 0.4994f, 0.4720f, 0.4442f,
                                            0.4442f, 0.4141f, 0.3828f, 0.3515f, 0.3205f, 0.2893f,
                                            0.2584f, 0.2267f, 0.1989f, 0.3256f, 0.3646f, 0.4044f,
                                            0.4446f, 0.4844f, 0.5236f, 0.5643f, 0.6037f, 0.6439f,
                                            0.6833f, 0.7234f, 0.7443f, 0.7443f, 0.7443f, 0.7443f,
                                            0.7026f, 0.6558f, 0.5554f, 0.5554f, 0.5554f, 0.5554f,
                                            0.5079f, 0.5079f, 0.5079f, 0.5079f, 0.5079f, 0.5276f,
                                            0.5616f, 0.5963f, 0.6315f, 0.6663f, 0.7010f, 0.7354f,
                                            0.7698f, 0.8049f, 0.8389f, 0.8740f, 0.8942f, 0.9258f,
                                            0.6999f, 0.6501f, 0.6002f, 0.5079f, 0.4754f, 0.4504f,
                                            0.4287f, 0.4287f, 0.4105f, 0.3793f, 0.3283f, 0.3040f,
                                            0.2317f, 0.1286f, 0.0985f, 0.0733f, 0.4500f, 0.4500f};
    private float[] tempStationHeightRate = {0.5542f, 0.5973f, 0.5996f, 0.5996f, 0.5996f, 0.5996f,
                                             0.5996f, 0.5996f, 0.5996f, 0.5996f, 0.5996f, 0.5996f,
                                             0.5996f, 0.5557f, 0.4988f, 0.4442f, 0.4027f, 0.4027f,
                                             0.4027f, 0.4027f, 0.4027f, 0.5973f, 0.5507f, 0.5335f,
                                             0.5335f, 0.5335f, 0.5335f, 0.5335f, 0.5335f, 0.6000f,
                                             0.6581f, 0.7023f, 0.7023f, 0.7023f, 0.7023f, 0.7023f,
                                             0.7023f, 0.7023f, 0.7023f, 0.8862f, 0.8862f, 0.8862f,
                                             0.8862f, 0.8862f, 0.8862f, 0.8862f, 0.8862f, 0.8862f,
                                             0.8862f, 0.8862f, 0.8554f, 0.8054f, 0.7561f, 0.7069f,
                                             0.6788f, 0.6788f, 0.6000f, 0.5327f, 0.4727f, 0.4427f,
                                             0.4031f, 0.3558f, 0.3173f, 0.2773f, 0.2358f, 0.2135f,
                                             0.2135f, 0.2135f, 0.2135f, 0.2135f, 0.2135f, 0.2135f,
                                             0.2135f, 0.2135f, 0.2135f, 0.2135f, 0.1646f, 0.1152f,
                                             0.4015f, 0.4015f, 0.4015f, 0.4015f, 0.4015f, 0.4015f,
                                             0.3392f, 0.2577f, 0.2146f, 0.2146f, 0.1719f, 0.1719f,
                                             0.1719f, 0.1719f, 0.1719f, 0.1719f, 0.1119f, 0.0804f};

    private ArrayList<Integer> mStationIds = new ArrayList<Integer>();
    private ArrayList<Float2> mStationCoordinates = new ArrayList<Float2>();
    private ArrayList<Float2> mDrawingPoints = new ArrayList<Float2>();

    public ROSView(Context context) {
        super(context);
    }

    public ROSView(Context context, AttributeSet attrs) {
        super(context, attrs);
        for(int i = 0; i < tempStationHeightRate.length; i++){
            mStationIds.add(tempStationIds[i]);
            mStationCoordinates.add(new Float2(tempStationWidthRate[i], tempStationHeightRate[i]));
        }
        setBackgroundResource(R.drawable.subway_map);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    public void setHighlight(int resId){
        BitmapFactory.Options options = new BitmapFactory.Options();
        highlight = BitmapFactory.decodeResource(getResources(), resId);
        Matrix matrix = new Matrix();
        matrix.postScale(highlightWidthCompressionRate, highlightHeightCompressionRate);
        highlight = Bitmap.createBitmap(highlight, 0, 0,
                                        highlight.getWidth(), highlight.getHeight(), matrix, true);

    }

    public void highlightStation(ArrayList<Integer> stationIds){
        mDrawingPoints.clear();
        for(int i = 0; i < stationIds.size(); i++){
            int stationId = stationIds.get(i);
            int index = mStationIds.indexOf(stationId);
            Float2 newPoint = new Float2(mStationCoordinates.get(index).x * getWidth(),
                                         mStationCoordinates.get(index).y * getHeight());
            mDrawingPoints.add(newPoint);
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for(int i = 0; i < mDrawingPoints.size(); i++){
            Float2 stationCoordinate = mDrawingPoints.get(i);
            canvas.drawBitmap(highlight, stationCoordinate.x - 0.5f * highlight.getWidth(),
                                         stationCoordinate.y - 0.5f * highlight.getHeight(), mPaint);
        }
    }
}