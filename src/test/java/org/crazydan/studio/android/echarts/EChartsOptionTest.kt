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
                triggerBy { axis }
                axisPointer {
                    type { cross }
                }
            }
            legend {
                type { plain }
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
                    filterMode { weakFilter }
                    window {
                        range(0.idx, 10.idx)
                    }
                }
                slider {
                    filterMode { filter }
                    margin {
                        top(90f.pct)
                        right(10f.pct)
                    }
                    window {
                        range(50f.pct, 100f.pct)
                    }
                }
            }
        }

        option.grid(id = "grid:0") {
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
                            item("2021-10-13") {}
                        }
                    }
                }
            }
            yAxis(id = "grid:0:y:0") {
                position { left() }

                name("血糖 (mmol/L)") { position { middle() } }
                type { value { fromZero(true) } }
                maxValue(40f)

//                    markLine {
//                        value(3.9f)
//                        name("<血糖>下限 (3.9 mmol/L)")
//                        label {
//                            formatter("{b}")
//                            position { insideStartTop() }
//                        }
//                    }
//                    markLine {
//                        value(15f)
//                        name("<血糖>上限 (15 mmol/L)")
//                        label {
//                            formatter("{b}")
//                            position { insideStartBottom() }
//                        }
//                    }
            }
        }

        option.series {
            line {
                name("S1")
                colorBy { data }
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
                    item(2, 5) {}
                }

                markArea {
                    byData {
                        name("数据区间")
                        label { formatter("{b}") }

                        start {
                            byDimension { max("y") }
                        }
                        end {
                            byDimension { min("y") }
                        }
                    }

                    byData {
                        name("观察窗口")
                        label { formatter("{b}") }

                        start {
                            byCoordinate(x = 1, y = 3f)
                        }
                        end {
                            byCoordinate(x = 2, y = 19f)
                        }
                    }
                    byYAxis {
                        value(6.1f, 7.8f)
                        name("空腹 8h (6.1 ~ 7.8 mmol/L)")
                        label {
                            position { insideLeft }
                            formatter("{b}")
                        }
                    }
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
                    item(2, 5, 9, 23, 31) {}
                }

                markLine {
                    byData {
                        name("最大差异")
                        symbol {
                            shape { circle }
                            size(10)
                        }
                        label {
                            position { middle }
                            formatter("{b}")
                        }

                        start {
                            byDimension { max("highest") }
                        }
                        end {
                            byDimension { min("lowest") }
                            symbol { rotate(0) }
                        }
                    }
                    byData {
                        symbol {
                            shape { pin }
                            size(30)
                            rotate(180)
                        }

                        start {
                            byCoordinate(x = 1, y = 3.1f)
                        }
                        end {
                            byCoordinate(x = 2, y = 15f)
                        }
                    }
                    byXAxis {
                        value("2021-10-12")
                        name("就医后")
                        label {
                            position { insideStartTop }
                            formatter("{b}")
                        }
                    }

                    byYAxis {
                        value(10f)
                        name("<餐后 2h>上限 (10 mmol/L)")
                        label {
                            position { insideStartBottom }
                            formatter("{b}")
                        }
                    }
                }

                markPoint {
                    byData {
                        symbol {
                            shape { pin }
                            size(50)
                        }
                        byDimension { max("highest") }
                    }
                    byData {
                        symbol {
                            shape { pin }
                            size(50)
                        }
                        byDimension { min("lowest") }
                    }
                    byData {
                        name("Start")
                        label {
                            position { bottom }
                            formatter("{b}")
                        }

                        symbol {
                            shape { circle }
                            size(50)
                        }
                        byCoordinate(x = 1, y = 15f)
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