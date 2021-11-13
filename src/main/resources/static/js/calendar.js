const GSTC = window.GSTC;
var calendarMemberList = window.calendarMemberList;
var calendarDateList = window.calendarDateList;


function listTemplate({ className, styleMap, list, listColumns, actions, slots, html, vido, props }) {
    return slots.html(
        "outer",
        cache(
            list.columns.percent > 0
                ? html`
            <div class=${className} data-actions=${actions()} style="height: 230px">
              ${slots.html(
                    "content",
                    listColumns.map((c) => c.html())
                )}
            </div>
          `
                : ""
        )
    );
}

function listColumnRowTemplate({
                                   classNameCurrent,
                                   classNameContent,
                                   styleMap,
                                   cache,
                                   shouldDetach,
                                   content,
                                   ListColumnRowExpander,
                                   slots,
                                   actions,
                                   html,
                                   vido,
                                   props,
                               }) {
    const detach = shouldDetach || !props || !props.column;
    return cache(
        detach
            ? null
            : slots.html(
                "outer",
                html`
            <div
              class=${classNameCurrent}
              data-gstcid=${props.row.id}
              style="height: 40px; --height:40px";
              data-actions=${actions()}
            >
              ${slots.html(
                    "inner",
                    html`
                  ${props.column.expander ? ListColumnRowExpander.html() : null}
                  <div class=${classNameContent}>${slots.html("content", content)}</div>
                `
                )}
            </div>
          `
            )
    );
}

function chartTimelineItemsRowItemTemplate({
                                               className,
                                               labelClassName,
                                               styleMap,
                                               cache,
                                               shouldDetach,
                                               cutterLeft,
                                               cutterRight,
                                               getContent,
                                               actions,
                                               slots,
                                               html,
                                               vido,
                                               props,
                                           }) {
    const detach = shouldDetach || !props || !props.item;
    return cache(
        detach
            ? null
            : slots.html(
                "outer",
                html`
            <div
              class=${className}
              data-gstcid=${props.item.id}
              data-actions=${actions()}
              style="width: 80px; left: 0px; top: 4px; height:32px"
            >
              ${slots.html(
                    "inner",
                    html`
                  ${cutterLeft()}
                  <div class=${labelClassName} style="justify-content: space-evenly; font-size: large; font-weight: bold">${slots.html("content", getContent())}</div>
                  ${cutterRight()}
                `
                )}
            </div>
          `
            )
    );
}

var dateFromDb = [];
for (const i in calendarDateList) {
    var date = {};
    date["id"]=calendarDateList[i]["id"];
    date["label"]=calendarDateList[i]["label"];
    date["rowId"]=calendarDateList[i]["rowId"];
    date["time"]={};
    date["time"]["start"] = GSTC.api.date(calendarDateList[i]["date"]).startOf("day").valueOf();
    date["time"]["end"] = GSTC.api.date(calendarDateList[i]["date"]).startOf("day").valueOf();

    console.log(dateFromDb);
    dateFromDb.push(date);
}


const columnsFromDB = [
    {
        id: "label",
        data: "label",
        sortable: "label",
        isHTML: false,
        width: 200,
        header: {
            content: "이름",
        },
    },
];

// Configuration object
const config = {
    // for free key for your domain please visit https://gstc.neuronet.io/free-key
    // if you need commercial license please visit https://gantt-schedule-timeline-calendar.neuronet.io/pricing
    licenseKey:
        "====BEGIN LICENSE KEY====\\npG53okwBVanAKzdgNexNjbJdIuIY75DOkixL42pgItwZKxValu5AM1IfU4G1qsOy6tO0YQMD1JlBw4RTPP26zIBYw4YrzHn3RNUrUbO8Fk2e+oUBl1eIn+1kpjL9jsvJCm9FwmxnyChzH+HEqqQl4SBauEuE5ydlq54xWfZc7edWWxW9CHaf90aV5rohU2gYo8JScuAiUp5k5KiMg4CowRtElZQeIH05sq/4LCn0Lhg2PGcUO009PnFBxuwoP025vk8X9U0xx64qCA6Fly5Mi78orO1/W0xlSZIlVMRt5tJ0x3UHtx5lQU+qK6VaXQQKRGO1JOibDJwg/wOqKkebEg==||U2FsdGVkX1+WFOTFnFLSYjdgSR3TBqcI2qkQpKsx4uqlgJBaCxikH/eC7b0e2nJMoB/LKwyKBXqMT36SLSCgt6jzoEWsWNQeR/tpsR6yHQc=\\noC/5ezuRFSI625V5a9hCuRh+e1beFiEbgfUHG6aFWsxmNo4p2QLyAxRHDw0osjXNS+13madlPN/4whMJaY3UdxUIkGhgAEEvguHmpG7BFesOmcHYC+GZxlO+hxGrulmHvs1SneJq+s/U1PCyIUElTaHlCxDnP3NYVzQ7VfdHbl5X2bU3zK5UaDbUAiXuWrBdPD0b4EJC2TpyOwqahNKQ83BehS1FxpMf4f+PCxxEp/4LVv+oWCdE7bcE5vmj868SIGR4JpvNOThGxqrFVNtE6aZT6IrM1jWZ7Hl0YXppi/AXLi8wsXYK2XohiYotN6kqmhUBtaSlIKMeYkp4FrhRgA==\\n====END LICENSE KEY====",

    templates: {
        "list-column-row": listColumnRowTemplate,
        "chart-timeline-items-row-item": chartTimelineItemsRowItemTemplate,
    },
    innerHeight: 230,
    list: {
        toggle:{
          display: false,
        },
        columns: {
            data: GSTC.api.fromArray(columnsFromDB),
        },
        rows: GSTC.api.fromArray(calendarMemberList),
    },
    chart: {
        items: GSTC.api.fromArray(dateFromDb),
    },
};

// Generate GSTC state from configuration object
const state = GSTC.api.stateFromConfig(config);

// Mount the component
const app = GSTC({
    element: document.getElementById("gstc"),
    state,
});