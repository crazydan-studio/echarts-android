package org.crazydan.studio.android.echarts

import org.junit.Test

/**
 *
 * @author <a href="mailto:flytreeleft@crazydan.org">flytreeleft</a>
 * @date 2025-09-08
 */
class EChartsOptionTest {
    val sampleData = mapOf(
        "2021-10-11" to listOf(9.6f, 7.8f, 8.2f),
        "2021-10-12" to listOf(7.8f, 8.9f, 6.8f),
        "2021-10-13" to listOf(8.3f, 7.2f, 9.8f, 6.9f),
        "2021-10-14" to listOf(9.3f, 8.1f),
        "2021-10-15" to listOf(7.5f, 7.8f, 6.9f),
    )

    @Test
    fun test_genCandlestick() {
        val option = commonOption(sampleData)

        option.series {
            candlestick {
                name("餐后")

                data {
                    dimension("x", "open", "close", "lowest", "highest") {
                        x("x")
                        y("open", "close", "lowest", "highest")
                        tooltip("open" to "最早", "close" to "最晚", "lowest" to "最低", "highest" to "最高")
                    }

                    sampleData.onEachIndexed { index, entry ->
                        item(
                            index,
                            entry.value.first(),
                            entry.value.last(),
                            entry.value.min(),
                            entry.value.max(),
                        ) {}
                    }
                }

                markLine {
                    byData {
                        name("最大波幅")
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

                    byYAxis {
                        value(10f)
                        name("<餐后>上限\n(10 mmol/L)")
                        label {
                            position { end }
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
                }
            }
        }

        val json = option.toJSON()
        println(json)
    }

    @Test
    fun test_genLine_with_candlestick() {
        val option = commonOption(sampleData)

        option.series {
            line {
                name("S1")
                colorBy { data }
                connectNulls(false)

                xAxisId("grid:0:x:0")
                yAxisId("grid:0:y:0")
            }
            line {
                name("空腹")
                connectNulls(true)

                data {
                    dimension("x", "y") {
                        x("x")
                        y("y")
                    }

                    item(0, 10) {}
                    item(1, null) {}
                    item(2, 7) {}
                    item(3, 6.8) {}
                    item(4, 7.2) {}
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
                            byCoordinate(x = 2, y = 14f)
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
                name("餐后")

                data {
                    dimension("x", "open", "close", "lowest", "highest") {
                        x("x")
                        y("open", "close", "lowest", "highest")
                        tooltip("open" to "最早", "close" to "最晚", "lowest" to "最低", "highest" to "最高")
                    }

                    sampleData.onEachIndexed { index, entry ->
                        item(
                            index,
                            entry.value.first(),
                            entry.value.last(),
                            entry.value.min(),
                            entry.value.max(),
                        ) {}
                    }
                }

                markLine {
                    byData {
                        name("最大波动")
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
                            byCoordinate(x = 2, y = 14f)
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
        println(json)
    }

    @Test
    fun get_genLineStack() {
        val option = commonOption(sampleData)

        option.series {
            line {
                name("餐后")
                smooth(true)
                connectNulls(true)

                symbol { shape { none } }
                stack { name("diff-range") }
                lineStyle { opacity(0.6f) }

                data {
                    dimension("x", "y") {
                        x("x")
                        y("y")
                    }

                    sampleData.onEachIndexed { index, entry ->
                        item(index, entry.value.min()) {}
                    }
                }
            }

            line {
                name("餐后")
                smooth(true)
                connectNulls(true)

                symbol { shape { none } }
                stack { name("diff-range") }
                lineStyle { opacity(0.6f) }
                areaStyle { opacity(0.6f) }

                data {
                    dimension("x", "y") {
                        x("x")
                        y("y")
                    }

                    // Note: 这里为前面同名 stack 之间的差值
                    sampleData.onEachIndexed { index, entry ->
                        item(index, entry.value.max() - entry.value.min()) {}
                    }
                }
            }
        }

        val json = option.toJSON()
        println(json)
    }

    @Test
    fun get_genScatter() {
        val option = commonOption(sampleData)

        option.series {
            scatter {
                name("餐后")
                colorBy { data }

                data {
                    dimension("x", "y") {
                        x("x")
                        y("y")
                    }

                    sampleData.onEachIndexed { index, entry ->
                        entry.value.forEach { value ->
                            item(index, value) {}
                        }
                    }
                }

                markLine {
                    byYAxis {
                        value(10f)
                        name("<餐后>上限\n(10 mmol/L)")
                        label {
                            position { end }
                            formatter("{b}")
                        }
                    }
                }
            }
        }

        val json = option.toJSON()
        println(json)
    }

    @Test
    fun get_genScatter_with_lineStack() {
        val option = commonOption(sampleData)

        option.series {
            scatter {
                name("餐后")
                colorBy { data }
                symbol { size(6) }

                data {
                    dimension("x", "y") {
                        x("x")
                        y("y")
                    }

                    sampleData.onEachIndexed { index, entry ->
                        entry.value.forEach { value ->
                            item(index, value) {}
                        }
                    }
                }

                markLine {
                    byYAxis {
                        value(10f)
                        name("<餐后>上限\n(10 mmol/L)")
                        label {
                            position { end }
                            formatter("{b}")
                        }
                    }
                }
            }

            line {
                name("餐后")
                smooth(true)
                connectNulls(true)

                symbol { shape { none } }
                stack { name("diff-range") }
                lineStyle { opacity(0.6f) }

                data {
                    dimension("x", "y") {
                        x("x")
                        y("y")
                    }

                    sampleData.onEachIndexed { index, entry ->
                        item(index, entry.value.min()) {}
                    }
                }
            }

            line {
                name("餐后")
                smooth(true)
                connectNulls(true)

                symbol { shape { none } }
                stack { name("diff-range") }
                lineStyle { opacity(0.6f) }
                areaStyle { opacity(0.6f) }

                data {
                    dimension("x", "y") {
                        x("x")
                        y("y")
                    }

                    // Note: 这里为前面同名 stack 之间的差值
                    sampleData.onEachIndexed { index, entry ->
                        item(index, entry.value.max() - entry.value.min()) {}
                    }
                }
            }
        }

        val json = option.toJSON()
        println(json)
    }

    private fun commonOption(sampleData: Map<String, List<Float>>): ECharts.Option {
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
                tick { alignWithLabel(true) }

                type {
                    category {
                        data {
                            sampleData.keys.forEach {
                                item(it) {}
                            }
                        }
                    }
                }
            }
            yAxis(id = "grid:0:y:0") {
                position { left() }

                type { value { fromZero(true) } }
                name("血糖 (mmol/L)") { position { middle() } }
                maxValue(15f)

                markLine {
                    value(3.9f)
                    name("<血糖>下限 (3.9 mmol/L)")
                    label {
                        formatter("{b}")
                        position { insideStartTop }
                    }
                }
                markLine {
                    value(12f)
                    name("<血糖>上限 (15 mmol/L)")
                    label {
                        formatter("{b}")
                        position { insideStartBottom }
                    }
                }
            }
        }

        return option
    }
}