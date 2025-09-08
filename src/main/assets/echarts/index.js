var myChart;
var myChartElement = 'container';
var myChartWrapper = window.__ChartWrapper__;

function initChart(useDarkTheme) {
    var dom = document.getElementById(myChartElement);

    myChart = echarts.init(dom, useDarkTheme ? 'dark' : null);

    // https://echarts.apache.org/en/api.html#events.rendered
    myChart.on('rendered', function () {
        myChartWrapper.onChartRendered();
    });

    myChartWrapper.onChartReady();
}

/** 更新图表配置及数据 */
function updateChartOptions(optionJson) {
    //console.log(optionJson);
    var option = JSON.parse(optionJson);

    // https://echarts.apache.org/en/option.html#title
    // https://echarts.apache.org/en/api.html#echartsInstance.setOption
    myChart.setOption(option);
}