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

    const contactModal = document.getElementById('contactModal');


    contactModal.addEventListener('show.bs.modal', function (event) {
        const button = event.relatedTarget; // 클릭한 링크
        const contactName = button.getAttribute('data-contact-name');
        const contactPhone = button.getAttribute('data-contact-phone');
        const contactQuestion = button.getAttribute('data-contact-question');

        // 모달 내용 업데이트
        document.getElementById('contactName').textContent = contactName || 'N/A';
        document.getElementById('contactPhone').textContent = contactPhone || 'N/A';
        document.getElementById('contactQuestion').textContent = contactQuestion || 'N/A';
    });
});
