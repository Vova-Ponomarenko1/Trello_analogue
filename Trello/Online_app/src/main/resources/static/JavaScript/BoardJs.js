
function toggleOptions(button) {
    const optionsMenu = button.nextElementSibling;
    optionsMenu.classList.toggle('show');
}

function deleteBoard(boardId) {
    fetch(`/delete/${boardId}`, {
        method: 'DELETE',
    })
        .then(response => response.json())
        .then(data => {
            console.log('Server response:', data);
        })
        .catch(error => {
            console.error('Error deleting board:', error);
        });
    window.location.href = `/boards`;
}



function openRenameBoardDialog(boardId) {
    const newName = prompt("Enter the new name for the board:");

    if (newName !== null) {
        updateNameBoard(boardId, newName);
        reloadPage();
    }
}

function updateNameBoard(boardId, newName) {
    fetch(`/updateNameBoard/${boardId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ newName: newName }),
    })
        .then(response => response.json())
        .then(data => {
            console.log('Server response:', data);
        })
        .catch(error => {
            console.error('Error updating board name:', error);
        });
    reloadPage();
}
function loadBoardDetails(boardId) {
    window.location.href = `/boards/${boardId}`;
}




function createBoard() {
    var boardName = prompt("Enter the name for the new board:");

    if (boardName === null || boardName.trim() === '') {
        alert('Board creation canceled or empty name provided.');
        return;
    }

    fetch('/create', {
        method: 'POST',
        headers: {
            'Content-Type': 'text/plain',
        },
        body: boardName,
    })
        .then(response => response.json())
        .then(data => {
            console.log('Server response:', data);

        })
        .catch(error => {
            window.location.href = "/boards";
            console.error('Error creating board:', error);
        });

    reloadPage();
}



function toggleOptions(button) {
    const optionsMenu = button.nextElementSibling;
    optionsMenu.classList.toggle('show');
}

function deleteBoard(boardId) {
    fetch(`/delete/${boardId}`, {
        method: 'DELETE',
    })
        .then(response => response.json())
        .then(data => {
            reloadPage();
        })
        .catch(error => {
            console.error('Error deleting board:', error);
        });
}



function openRenameBoardDialog(boardId) {
    const newName = prompt("Enter the new name for the board:");

    if (newName !== null) {
        updateNameBoard(boardId, newName);
    }
}

function updateNameBoard(boardId, newName) {
    fetch(`/updateNameBoard/${boardId}`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify({ newName: newName }),
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Failed to create board');
            }
            return response.json();
        })
        .then(data => {
            console.log('Server response:', data);
        })
        .catch(error => {
            alert('Invalid board name');
        });
    reloadPage();
}
function loadBoardDetails(boardId) {
    window.location.href = `/boards/${boardId}`;
}