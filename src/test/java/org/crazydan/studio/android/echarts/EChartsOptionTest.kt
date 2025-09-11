package org.crazydan.studio.android.echarts

import org.junit.Assert
import org.junit.Test

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-08
 */
class EChartsOptionTest {

    @Test
    fun test_toJSON() {
        val option = ECharts.option {
            theme {
                backgroundColor(rgba(0x141218))
                seriesColors(
                    listOf(
                        rgba(0x5470c6),
                        rgba(0x91cc75),
                        rgba(0xfac858),
                        rgba(0xee6666),
                        rgba(0x73c0de),
                        rgba(0x3ba272),
                        rgba(0xfc8452),
                        rgba(0x9a60b4),
                        rgba(0xea7ccc),
                    )
                )
            }
            tooltip {
                triggerBy { axis() }
                axisPointer {
                    type { cross() }
                }
            }
            legend {
                type { plain() }
                margin {
                    top(20.px)
                }
            }
            dataZoom {
                inside {
                    disabled(true)
                }
                slider {
                    show(false)
                }

                inside {
                    filterMode { weakFilter() }
                    window {
                        range(0.idx, 10.idx)
                    }
                }
                slider {
                    filterMode { filter() }
                    margin {
                        top(90f.pct)
                        right(10f.pct)
                    }
                    window {
                        range(50f.pct, 100f.pct)
                    }
                }
            }
            grid(id = "grid:0") {
                showBorder(false)
                margin {
                    horizontal(10f.pct)
                    bottom(15f.pct)
                }

                xAxis(id = "grid:0:x:0") {
                    position { bottom(5.px) }
                    axisTick { alignWithLabel(true) }

                    type {
                        category {
                            data {
                                item("2021-10-11") {}
                                item("2021-10-12") {}
                            }
                        }
                    }
                }
                yAxis(id = "grid:0:y:0") {
                    position { left() }

                    type { value { fromZero(true) } }
                }
            }

            series {
                line {
                    name("S1")
                    colorBy { data() }
                    connectNulls(false)
                    xAxisId("grid:0:x:0")
                    yAxisId("grid:0:y:0")
                }
                line {
                    name("S2")
                    connectNulls(true)

                    data {
                        dimension("x", "y") {
                            x("x")
                            y("y")
                        }

                        item(0, 10) {}
                        item(1, null) {}
                    }
                }

                candlestick {
                    name("S3")

                    data {
                        dimension("x", "open", "close", "lowest", "highest") {
                            x("x")
                            y("open", "close", "highest", "lowest")
                            tooltip("open" to "最早", "close" to "最晚", "lowest" to "最高", "highest" to "最低")
                        }

                        item(0, 10, 11, 10, 13) {}
                        item(1, 8, 6, 10, 21) {}
                    }
                }
            }
        }

        val json = option.toJSON()
        Assert.assertEquals(
            "{\"tooltip\": {\"axisPointer\": {\"type\": \"cross\"},\"backgroundColor\": \"rgba(50, 50, 50, 0.7019608)\",\"borderColor\": \"rgba(3, 3, 3, 1.0)\",\"show\": true,\"trigger\": \"axis\"},\"legend\": {\"show\": true,\"top\": 20,\"type\": \"plain\"},\"grid\": {\"bottom\": \"15.0%\",\"left\": \"10.0%\",\"right\": \"10.0%\",\"show\": true},\"series\": [{\"data\": [{\"value\": [10]},{\"value\": [21]}],\"name\": \"Abc\",\"xAxisIndex\": 0,\"yAxisIndex\": 0,\"smooth\": true,\"type\": \"line\"},{\"data\": [{\"value\": [10,11,10,13]},{\"value\": [10,11,10,13]}],\"markLine\": {\"data\": [[{\"symbol\": \"circle\",\"symbolSize\": 10,\"type\": \"max\",\"valueDim\": \"highest\"},{\"symbol\": \"circle\",\"symbolSize\": 10,\"type\": \"min\",\"valueDim\": \"lowest\"}]],\"silent\": true},\"markPoint\": {\"data\": [{\"type\": \"max\",\"valueDim\": \"highest\"},{\"type\": \"min\",\"valueDim\": \"lowest\"}],\"silent\": true},\"name\": \"Def\",\"xAxisIndex\": 0,\"yAxisIndex\": 0,\"smooth\": true,\"type\": \"candlestick\"}],\"xAxis\": [{\"data\": [{\"value\": \"2021-10-11\"},{\"value\": \"2021-10-12\"}],\"scale\": false,\"show\": true,\"type\": \"category\"}],\"yAxis\": [{\"scale\": true,\"show\": true,\"type\": \"value\"}],\"dataZoom\": [{\"endValue\": 1,\"filterMode\": \"filter\",\"show\": true,\"startValue\": 0,\"top\": \"90.0%\",\"type\": \"slider\"}]}",
            json
        )
    }
}