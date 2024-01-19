/* GRUD column*/
function startEditing(element) {
    element.contentEditable = "true";
    element.focus();
    element.addEventListener("blur", function () {
        element.contentEditable = "false";

        updateColumnName(element);
    });
}

function updateColumnName(element) {
    const columnId = element.getAttribute('data-columnId') || element.dataset.columnId;
    const newName = element.innerText;

    fetch(`/api/column/updateName/${columnId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ newName: newName }),
    })
        .then(response => response.json())
        .then(data => {
            alert(data.message || 'Column created successfully');
            reloadPage();
        })
        .catch(error => {
            console.error('Error updating column name:', error);
        });
}



function deleteColumn(element) {
    const columnId = element.getAttribute('data-columnId');

    fetch(`/api/column/delete/${columnId}`, {
        method: 'DELETE',
    })
        .then(response => response.json())
        .then(data => {
            console.log('Server response:', data);
        })
        .catch(error => {
            console.error('Error deleting column:', error);
        });
    reloadPage();
}

function createNewColumn(boardId) {
    const columnName = prompt('Enter the name for the new column:', '');

    if (columnName === null || columnName.trim() === '') {
        alert('Column creation canceled or empty name provided.');
        return;
    }
    fetch(`/api/column/create/${boardId}`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ columnName: columnName }),
    })
        .then(response => response.json())
        .then(data => {
            alert(data.message || 'Column created successfully');
            reloadPage();
        })
}