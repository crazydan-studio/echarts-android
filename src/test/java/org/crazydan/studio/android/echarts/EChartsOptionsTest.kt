package org.crazydan.studio.android.echarts

import org.crazydan.studio.android.echarts.option.AxisData
import org.crazydan.studio.android.echarts.option.AxisType
import org.crazydan.studio.android.echarts.option.DataZoomType
import org.crazydan.studio.android.echarts.option.Series
import org.crazydan.studio.android.echarts.option.SeriesMarkData
import org.crazydan.studio.android.echarts.option.SeriesMarkLine
import org.crazydan.studio.android.echarts.option.SeriesMarkPoint
import org.crazydan.studio.android.echarts.option.Size
import org.crazydan.studio.android.echarts.option.TooltipAxisPointer
import org.crazydan.studio.android.echarts.option.TooltipTrigger
import org.crazydan.studio.android.echarts.option.dataZoom
import org.crazydan.studio.android.echarts.option.grid
import org.crazydan.studio.android.echarts.option.legend
import org.crazydan.studio.android.echarts.option.series
import org.crazydan.studio.android.echarts.option.tooltip
import org.crazydan.studio.android.echarts.option.xAxis
import org.crazydan.studio.android.echarts.option.yAxis
import org.junit.Assert
import org.junit.Test

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-08
 */
class EChartsOptionsTest {

    @Test
    fun test_toJSON() {
        val options = EChartsOptions.tooltip(
            trigger = TooltipTrigger.Axis,
            axisPointer = TooltipAxisPointer(
                type = TooltipAxisPointer.Type.Cross,
            ),
        ).legend(
            top = Size.pixel(20),
        ).grid(
            left = Size.percent(10f),
            right = Size.percent(10f),
            bottom = Size.percent(15f),
        ).xAxis(
            type = AxisType.Category,
            data = listOf(
                AxisData(value = "2021-10-11"),
                AxisData(value = "2021-10-12"),
            ),
        ).yAxis(
            scale = true,
        ).dataZoom(
            type = DataZoomType.Slider,
            top = Size.percent(90f),
            startValueIndex = 0,
            endValueIndex = 1,
        ).series(
            listOf(
                Series.Line(
                    name = "Abc",
                    data = listOf(
                        Series.Line.Data(value = 10),
                        Series.Line.Data(value = 21),
                    ),
                ),
                Series.Candlestick(
                    name = "Def",
                    data = listOf(
                        Series.Candlestick.Data(value = listOf(10, 11, 10, 13)),
                        Series.Candlestick.Data(value = listOf(10, 11, 10, 13)),
                    ),
                    markPoint = SeriesMarkPoint(
                        data = listOf(
                            SeriesMarkData(
                                type = SeriesMarkData.Type.Max,
                                valueDim = "highest",
                            ),
                            SeriesMarkData(
                                type = SeriesMarkData.Type.Min,
                                valueDim = "lowest",
                            ),
                        ),
                    ),
                    markLine = SeriesMarkLine(
                        data = listOf(
                            SeriesMarkLine.Range(
                                from = SeriesMarkData(
                                    type = SeriesMarkData.Type.Max,
                                    valueDim = "highest",
                                    symbol = SeriesMarkData.Symbol.Circle,
                                    symbolSize = 10,
                                ),
                                to = SeriesMarkData(
                                    type = SeriesMarkData.Type.Min,
                                    valueDim = "lowest",
                                    symbol = SeriesMarkData.Symbol.Circle,
                                    symbolSize = 10,
                                ),
                            ),
                        ),
                    ),
                )
            )
        )

        val json = options.toJSON()
        Assert.assertEquals(
            "{\"tooltip\": {\"axisPointer\": {\"type\": \"cross\"},\"backgroundColor\": \"rgba(50, 50, 50, 0.7019608)\",\"borderColor\": \"rgba(3, 3, 3, 1.0)\",\"show\": true,\"trigger\": \"axis\"},\"legend\": {\"show\": true,\"top\": 20,\"type\": \"plain\"},\"grid\": {\"bottom\": \"15.0%\",\"left\": \"10.0%\",\"right\": \"10.0%\",\"show\": true},\"series\": [{\"data\": [{\"value\": [10]},{\"value\": [21]}],\"name\": \"Abc\",\"xAxisIndex\": 0,\"yAxisIndex\": 0,\"smooth\": true,\"type\": \"line\"},{\"data\": [{\"value\": [10,11,10,13]},{\"value\": [10,11,10,13]}],\"markLine\": {\"data\": [[{\"symbol\": \"circle\",\"symbolSize\": 10,\"type\": \"max\",\"valueDim\": \"highest\"},{\"symbol\": \"circle\",\"symbolSize\": 10,\"type\": \"min\",\"valueDim\": \"lowest\"}]],\"silent\": true},\"markPoint\": {\"data\": [{\"type\": \"max\",\"valueDim\": \"highest\"},{\"type\": \"min\",\"valueDim\": \"lowest\"}],\"silent\": true},\"name\": \"Def\",\"xAxisIndex\": 0,\"yAxisIndex\": 0,\"smooth\": true,\"type\": \"candlestick\"}],\"xAxis\": [{\"data\": [{\"value\": \"2021-10-11\"},{\"value\": \"2021-10-12\"}],\"scale\": false,\"show\": true,\"type\": \"category\"}],\"yAxis\": [{\"scale\": true,\"show\": true,\"type\": \"value\"}],\"dataZoom\": [{\"endValue\": 1,\"filterMode\": \"filter\",\"show\": true,\"startValue\": 0,\"top\": \"90.0%\",\"type\": \"slider\"}]}",
            json
        )
    }
}