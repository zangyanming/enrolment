define(['knockout','util'], function (ko,Util) {
    function ViewModel(params) {
        var scope = this;

        var chart1Items = [
            {Day: 'Monday', Keith: 30, Erica: 15, George: 25},
            {Day: 'Tuesday', Keith: 25, Erica: 25, George: 30},
            {Day: 'Wednesday', Keith: 30, Erica: 20, George: 25},
            {Day: 'Thursday', Keith: 35, Erica: 25, George: 45},
            {Day: 'Friday', Keith: 20, Erica: 20, George: 25},
            {Day: 'Saturday', Keith: 30, Erica: 20, George: 30},
            {Day: 'Sunday', Keith: 60, Erica: 45, George: 90}
        ];

        Util.ajaxGet(Util.getAjaxUrl('/mq/shortMessage/logChartByMessageType'),{},function (data) {
            if(data.success){
                scope.chart1.settings.source(data.data);
                scope.chart2.settings.source(data.data);
            }
        });
        Util.ajaxGet(Util.getAjaxUrl('/mq/sysLogSysmsg/logChartByName'),{},function (data) {
            if(data.success){
                scope.chart3.settings.source(data.data);
            }
        });
        Util.ajaxGet(Util.getAjaxUrl('/mq/sysLogSysmsg/logChartByMonth'),{},function (data) {
            if(data.success){
                scope.chart4.settings.source(data.data);
            }
        });
        Util.ajaxGet(Util.getAjaxUrl('/mq/shortMessage/logChartByMonth'),{},function (data) {
            if(data.success){
                scope.chart5.settings.source(data.data);
            }
        });
        Util.ajaxGet(Util.getAjaxUrl('/email/sysLogEmail/logChartByMonth'),{},function (data) {
            if(data.success){
                scope.chart6.settings.source(data.data);
            }
        });
        scope.chart1 = {
            height: 400,
            settings: {
                title: "消息汇总",
                description: "",
                showLegend: false,
                source: ko.observableArray(),
                xAxis: {dataField: "name",},
                valueAxis: {
                    unitInterval: 10,
                    minValue: 0,
                    maxValue: 100,
                    description: "",
                },
                seriesGroups:
                    [
                        {
                            type: "column",
                            series: [
                                {dataField: "cnt", displayText: "总数"}
                            ]
                        }
                    ]
            }
        };
        scope.chart2 = {
            height: 400,
            settings: {
                title: "消息汇总",
                description: "",
                showLegend: false,
                source: ko.observableArray(),
                seriesGroups:
                    [
                        {
                            type: 'pie',
                            showLabels: true,
                            series:
                                [
                                    {
                                        dataField: 'cnt',
                                        displayText: 'name',
                                        labelRadius: 120,
                                        initialAngle: 15,
                                        radius: 170,
                                        centerOffset: 0
                                    }
                                ]
                        }
                    ]
            }
        };
        scope.chart3 = {
            height: 400,
            settings: {
                title: "系统消息-名称",
                description: "",
                showLegend: false,
                source: ko.observableArray(),
                xAxis: {dataField: "name",},
                colorScheme:'scheme02',
                valueAxis: {
                    unitInterval: 10,
                    minValue: 0,
                    maxValue: 100,
                    description: "",
                },
                seriesGroups:
                    [
                        {
                            type: "column",
                            series: [
                                {dataField: "cnt", displayText: "总数"}
                            ]
                        }
                    ]
            }
        };
        scope.chart4 = {
            height: 400,
            settings: {
                title: "系统消息-月份",
                description: "",
                showLegend: false,
                source: ko.observableArray(),
                xAxis: {dataField: "name",},
                colorScheme:'scheme04',
                valueAxis: {
                    unitInterval: 10,
                    minValue: 0,
                    maxValue: 100,
                    description: "",
                },
                seriesGroups:
                    [
                        {
                            type: "column",
                            series: [
                                {dataField: "cnt", displayText: "总数"}
                            ]
                        }
                    ]
            }
        };
        scope.chart5 = {
            height: 400,
            settings: {
                title: "短信息-月份",
                description: "",
                showLegend: false,
                source: ko.observableArray(),
                xAxis: {dataField: "name",},
                colorScheme:'scheme06',
                valueAxis: {
                    unitInterval: 10,
                    minValue: 0,
                    maxValue: 100,
                    description: "",
                },
                seriesGroups:
                    [
                        {
                            type: "column",
                            series: [
                                {dataField: "cnt", displayText: "总数"}
                            ]
                        }
                    ]
            }
        };
        scope.chart6 = {
            height: 400,
            settings: {
                title: "电子邮件-月份",
                description: "",
                showLegend: false,
                source: ko.observableArray(),
                xAxis: {dataField: "name",},
                colorScheme:'scheme05',
                valueAxis: {
                    unitInterval: 10,
                    minValue: 0,
                    maxValue: 100,
                    description: "",
                },
                seriesGroups:
                    [
                        {
                            type: "column",
                            series: [
                                {dataField: "cnt", displayText: "总数"}
                            ]
                        }
                    ]
            }
        };
    }

    return ViewModel;
});
