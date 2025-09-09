package org.crazydan.studio.android.echarts

import org.crazydan.studio.android.echarts.option.percent
import org.crazydan.studio.android.echarts.option.pixel
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
        val option = ECharts.option {
            tooltip {
                triggerByAxis()
                axisPointer {
                    type { cross() }
                }
            }
            legend {
                type { plain() }
                margin {
                    top(20.pixel)
                }
            }
            grid {
                showBorder(false)
                margin {
                    left(10f.percent)
                    right(10f.percent)
                    bottom(15f.percent)
                }
            }
            xAxis {
                type { category() }
            }
            yAxis {
                type { value { fromZero(true) } }
            }
        }

        val options = EChartsOptions.xAxis(
            type = AxisType.Category,
            data = listOf(
                AxisData(value = "2021-10-11"),
                AxisData(value = "2021-10-12"),
            ),
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

        val json = option.toJSON()
        Assert.assertEquals(
            "{\"tooltip\": {\"axisPointer\": {\"type\": \"cross\"},\"backgroundColor\": \"rgba(50, 50, 50, 0.7019608)\",\"borderColor\": \"rgba(3, 3, 3, 1.0)\",\"show\": true,\"trigger\": \"axis\"},\"legend\": {\"show\": true,\"top\": 20,\"type\": \"plain\"},\"grid\": {\"bottom\": \"15.0%\",\"left\": \"10.0%\",\"right\": \"10.0%\",\"show\": true},\"series\": [{\"data\": [{\"value\": [10]},{\"value\": [21]}],\"name\": \"Abc\",\"xAxisIndex\": 0,\"yAxisIndex\": 0,\"smooth\": true,\"type\": \"line\"},{\"data\": [{\"value\": [10,11,10,13]},{\"value\": [10,11,10,13]}],\"markLine\": {\"data\": [[{\"symbol\": \"circle\",\"symbolSize\": 10,\"type\": \"max\",\"valueDim\": \"highest\"},{\"symbol\": \"circle\",\"symbolSize\": 10,\"type\": \"min\",\"valueDim\": \"lowest\"}]],\"silent\": true},\"markPoint\": {\"data\": [{\"type\": \"max\",\"valueDim\": \"highest\"},{\"type\": \"min\",\"valueDim\": \"lowest\"}],\"silent\": true},\"name\": \"Def\",\"xAxisIndex\": 0,\"yAxisIndex\": 0,\"smooth\": true,\"type\": \"candlestick\"}],\"xAxis\": [{\"data\": [{\"value\": \"2021-10-11\"},{\"value\": \"2021-10-12\"}],\"scale\": false,\"show\": true,\"type\": \"category\"}],\"yAxis\": [{\"scale\": true,\"show\": true,\"type\": \"value\"}],\"dataZoom\": [{\"endValue\": 1,\"filterMode\": \"filter\",\"show\": true,\"startValue\": 0,\"top\": \"90.0%\",\"type\": \"slider\"}]}",
            json
        )
    }
}