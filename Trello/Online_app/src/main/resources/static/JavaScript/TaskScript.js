function openTaskDetails(taskId) {
    fetch('/api/task/' + taskId)
        .then(response => response.json())
        .then(data => {
            document.getElementById('popupTaskName').innerText = data.taskName;
            document.getElementById('popupTaskDescription').innerText = data.taskDescription;
            const createdAt = moment(data.createdAt);
            const formattedCreatedAt = createdAt.format('YYYY-MM-DD HH:mm:ss');
            document.getElementById('popupTaskCreatedAt').innerText = 'Created at: ' + formattedCreatedAt;

            sendTaskName('popupTaskName', taskId);
            saveTaskDescription('popupTaskDescription', taskId);
            document.querySelector('.delete-task-button').addEventListener('click', function() {
                sendTaskDelete('popupTaskDelete', taskId);
            });
            document.getElementById('taskPopup').style.display = 'block';
            document.getElementById('overlay').style.display = 'block';
        })
        .catch(error => {
            console.error('Error fetching task details:', error);
        });
}

function closePopup() {
    location.reload();
    document.getElementById('taskPopup').style.display = 'none';
    document.getElementById('overlay').style.display = 'none';
}
function reloadPage() {
    setTimeout(function () {
        location.reload();
    }, 1000);
}



//changing task data name and description
function sendTaskDelete(elementId, taskIdNum) {
    console.log("delete met")
    const taskIdString = taskIdNum.toString();
    const taskId = parseInt(taskIdString, 10);

    if (isNaN(taskId)) {
        console.error('Invalid taskId:', taskIdString);
        return;
    }
    fetch(`/api/task/delete/${taskId}`, {
        method: 'DELETE',
    })
        .then(response => {
            if (!response.ok) {
                throw new Error(`Error deleting task ${taskId}: ${response.statusText}`);
            }
            console.log(`Task ${taskId} deleted successfully`);
        })
        .catch(error => {
            console.error('Error deleting task:', error);
        });
}

function sendTaskName(elementId, taskId) {
    const taskIdString = taskId.toString();
    const taskIdNum = parseInt(taskIdString, 10);

    if (isNaN(taskIdNum)) {
        console.error('Invalid taskId:', taskIdString);
        return;
    }
    const taskNameElement = document.getElementById(elementId);

    taskNameElement.onblur = function () {
        const taskName = taskNameElement.innerText;
        console.log(taskIdNum);
        fetch('/api/task/' + taskIdNum + '/name', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ taskName: taskName }),
        })
            .then(response => response.json()
            )
            .then(data => {
                console.log('Task name updated:', data);
            })
            .catch(error => {
                console.error('Error updating task name:', error);
            });
    };

}


function saveTaskDescription(elementId, taskId) {
    const taskIdString = taskId.toString();
    const taskIdNum = parseInt(taskIdString, 10);

    if (isNaN(taskIdNum)) {
        console.error('Invalid taskId:', taskIdString);
        return;
    }
    const taskDescriptionElement = document.getElementById(elementId);

    taskDescriptionElement.onblur = function () {
        const taskDescription = taskDescriptionElement.innerText;
        console.log(taskIdNum);
        fetch('/api/task/' + taskIdNum + '/description', {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ taskDescription: taskDescription }),
        })
            .then(response => response.json())
            .then(data => {
                console.log('Task description updated:', data);
            })
            .catch(error => {
                console.error('Error updating task description:', error);
            });

    };
}
function openNewTaskForm(columnId) {
    document.getElementById(`taskForm-${columnId}`).style.display = 'block';
}

function createTask(event, columnId) {
    event.preventDefault();
    const taskName = document.querySelector(`#taskForm-${columnId} #taskName`).value;
    const taskDescription = document.querySelector(`#taskForm-${columnId} #taskDescription`).value;

    const formData = {
        taskName: taskName,
        taskDescription: taskDescription
    };

    fetch(`/api/task/create/${columnId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(formData),
    })
        .then(response => response.json())
        .then(data => {})
        .catch(error => {
            console.error('Error:', error);
        });

    document.getElementById(`taskForm-${columnId}`).style.display = 'none';
}