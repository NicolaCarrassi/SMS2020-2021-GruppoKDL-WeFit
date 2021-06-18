package it.uniba.di.sms2021.gruppokdl.wefit.utility;


import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * La classe contiene i metodi statici che permettono di gestire le
 * configurazioni grafiche del plot
 */
public class GraphSettings {

    /**
     * Il metodo permette di inserire all'interno del grafico due plot
     * Uno contenente una interpolazione di una serie di punti e l'altro
     * contenente una serie di punti, dati dalle coordinate di xList e yList
     *
     * @param graph grafico su cui inserire i due plot
     * @param xList coordinate x dei punti
     * @param yList coordinate y dei punti
     */
    public static void graphSettings(GraphView graph, List<Date> xList, List<Float> yList){
        int removedItems = 0;
        LineGraphSeries<DataPoint> line = new LineGraphSeries<>(new DataPoint[]{});
        PointsGraphSeries<DataPoint> points = new PointsGraphSeries<>(new DataPoint[]{});
        List<DataPoint> list = new ArrayList<>();


        for(int i = 0; i<yList.size(); i++){
            if (i > 0) {
                if (xList.get(i).compareTo(xList.get(i - 1)) == 0) {
                    removedItems++;
                    list.remove(i - removedItems);
                }
            }
            list.add(new DataPoint(xList.get(i), yList.get(i)));
        }

        for(DataPoint dp: list){
            line.appendData(dp, true, 10);
            points.appendData(dp, true, 10);
        }


        graph.addSeries(line);
        graph.addSeries(points);
        graph.getViewport().setXAxisBoundsManual(true);
        graph.getViewport().setYAxisBoundsManual(true);
        graph.getViewport().setScrollable(true);



        points.setShape(PointsGraphSeries.Shape.POINT);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);

    }


}
