package it.uniba.di.sms2021.gruppodkl.wefit.utility;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.jjoe64.graphview.series.PointsGraphSeries;

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
        LineGraphSeries<DataPoint> line = new LineGraphSeries<>(new DataPoint[]{});
        PointsGraphSeries<DataPoint> points = new PointsGraphSeries<>(new DataPoint[]{});

        for (int i =0 ; i < yList.size(); i++) {
            line.appendData(new DataPoint(xList.get(i), yList.get(i)), true, 10);
            points.appendData(new DataPoint(xList.get(i), yList.get(i)), true, 10);
        }

        graph.addSeries(line);
        graph.addSeries(points);

        points.setShape(PointsGraphSeries.Shape.POINT);
        graph.getGridLabelRenderer().setHorizontalLabelsVisible(false);

    }


}
