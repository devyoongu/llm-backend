document.addEventListener('DOMContentLoaded', function () {
    const chatLogsModal = document.getElementById('chatLogsModal');
    const chatLogsContainer = document.getElementById('chatLogsContainer');

    chatLogsModal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget;
        const chatLogsJson = button.getAttribute('data-chat-logs');
        const chatLogs = JSON.parse(chatLogsJson);

        // 기존 메시지 초기화
        chatLogsContainer.innerHTML = '';

        // 채팅 로그를 채팅창 스타일로 렌더링
        chatLogs.forEach(log => {
            const messageDiv = document.createElement('div');
            messageDiv.classList.add('chat-message', log.role === 'assistant' ? 'assistant' : 'user');

            const bubbleDiv = document.createElement('div');
            bubbleDiv.classList.add('message-bubble', log.role === 'assistant' ? 'assistant' : 'user');
            bubbleDiv.textContent = log.content;

            const timeDiv = document.createElement('div');
            timeDiv.classList.add('message-time');
            timeDiv.textContent = new Date(log.createdTime).toLocaleTimeString();

            messageDiv.appendChild(bubbleDiv);
            messageDiv.appendChild(timeDiv);
            chatLogsContainer.appendChild(messageDiv);
        });
    });
});
