document.addEventListener("DOMContentLoaded", function () {
    const columnContainer = document.querySelector(".column-container");
    const lists = document.querySelectorAll(".list");

    new Sortable(columnContainer, {
        group: "board",
        animation: 150,
        ghostClass: "drag-over",
        onEnd: function (event) {
            synchronizeColumnPositions(event);
        },
    });

    lists.forEach(function (list) {
        new Sortable(list, {
            group: "list",
            animation: 150,
            ghostClass: "drag-over",
            onEnd: function (event) {
                synchronizeTaskPositions();
            },
        });
    });

    function synchronizeColumnPositions(event) {
        const positions = Array.from(columnContainer.children).map(function (column, index) {
            return {
                columnId: parseInt(column.getAttribute("data-columnId")),
                position: index,
                boardId: parseInt(column.getAttribute("data-boardId"))
            };
        });

        fetch("/api/column/boards/synchronize-column-positions", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(positions),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.text();
            })
            .then(data => {
                console.log(data);
            })
            .catch(error => {
                console.error('Fetch error:', error);
            });
    }

    function synchronizeTaskPositions() {
        const lists = document.querySelectorAll(".list");

        const positions = [];
        lists.forEach(function (list) {
            const columnId = list.closest(".column").getAttribute("data-columnId");
            const taskBoxes = list.querySelectorAll(".task-box");

            taskBoxes.forEach(function (task, index) {
                positions.push({
                    taskId: parseInt(task.getAttribute("data-taskId")),
                    columnId: parseInt(columnId),
                    position: index
                });
            });
        });

        fetch("/api/task/boards/synchronize-task-positions", {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(positions),
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error(`HTTP error! Status: ${response.status}`);
                }
                return response.text();
            })
            .then(data => {
                console.log(data);
            })
            .catch(error => {
                console.error('Fetch error:', error);
            });
    }

});