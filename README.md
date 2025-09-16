# Android Kotlin ECharts

[ECharts](https://echarts.apache.org) kotlin `@Composable` for Android.

It will render charts in `WebView`, we just provide DSL syntax
for [EChats Option](https://echarts.apache.org/zh/option.html).

## How to use

- Clone this repository to your project root directory:

  ```bash
  git clone git@github.com:crazydan-studio/echarts-android.git
  ```

- Include `echarts` module in your `/settings.gradle.kts`:

  ```kotlin
  include(":echarts")
  ```

- Add dependency to `build.gradle.kts` in the module
  which needs to render charts, such as `/app/build.gradle.kts`
  in `app` module:

  ```kotlin
  dependencies {
    implementation(project(":echarts"))
  }
  ```

- Go to https://echarts.apache.org/zh/builder.html to custom your ECharts library package,
  and download the `echarts.min.js` to `src/main/assets/echarts` in your module.

  > Package the mimimal requirements will reduce your app size

- Configure `ECharts` in your composable function:
  ```kotlin
  @Composable
  fun YourCharts(modifier: Modifier = Modifier) {
    ECharts(
      modifier = modifier,
      useDarkTheme = isSystemInDarkTheme(),
      option = ECharts.option {
        tooltip { ... }
        legend { ... }
        dataZoom { ... }
        grid { ... }
        series {
          line { ... }
          candlestick { ... }
          scatter { ... }
        }
      },
    )
  }
  ```

## Examples

- Configure the basic option:
  ```kotlin
  val option = ECharts.option {
    theme {
      backgroundColor(rgba(0x141218))
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
  ```

- Configure the `grid`:
  ```kotlin
  option.grid {
    showBorder(false)
    margin {
      horizontal(10f.pct)
      bottom(15f.pct)
    }

    xAxis {
      position { bottom(5.px) }
      axisTick { alignWithLabel(true) }

      type {
        category {
          data {
            item("2025-10-01") {}
            item("2025-10-02") {}
            item("2025-10-03") {}
            item("2025-10-04") {}
            item("2025-10-05") {}
          }
        }
      }
    }
    yAxis {
      position { left() }

      type { value { fromZero(true) } }
      name("血糖 (mmol/L)") { position { middle() } }

      // 所有图例公共的标记线
      markLine {
        value(3.9f)
        name("<血糖>下限\n(3.9 mmol/L)")
        label {
          formatter("{b}")
          position { insideStartTop }
        }
      }
      markLine {
        value(12f)
        name("<血糖>上限\n(15 mmol/L)")
        label {
          formatter("{b}")
          position { insideStartBottom }
        }
      }
    }
  }
  ```

- Configure the `series`:
  ```kotlin
  option.series {
    // 线图
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
        byYAxis {
          value(6.1f, 7.8f)

          name("空腹\n(6.1 ~ 7.8 mmol/L)")
          label {
            position { insideLeft }
            formatter("{b}")
          }
        }
      }
    }

    // 散点图
    scatter {
      name("餐后")
      colorBy { data }

      data {
        dimension("x", "y") {
          x("x")
          y("y")
        }

        item(0, 9.8) {}
        item(0, 7.5) {}
        item(0, 6.3) {}
        item(0, 8.2) {}
        item(1, 6.4) {}
        item(1, 8.1) {}
        item(1, 6.9) {}
        item(2, 8.4) {}
        item(2, 8.2) {}
        item(2, 7.9) {}
        item(2, 9.1) {}
        item(3, 5.7) {}
        item(3, 8.2) {}
        item(3, 6.9) {}
        item(3, 6.2) {}
        item(4, 7.3) {}
        item(4, 8.1) {}
        item(4, 6.8) {}
        item(4, 9.0) {}
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
  ```

## Custom

If you want to custom ECharts library, you can create file
`custom.css` and `custom.js` in the `src/main/assets/echarts`.

## License

[Apache-2.0](./LICENSE)
