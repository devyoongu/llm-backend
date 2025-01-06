document.addEventListener('DOMContentLoaded', function () {

    const sidebarToggle = document.body.querySelector('#sidebarToggle');
    if (sidebarToggle) {
        // Uncomment Below to persist sidebar toggle between refreshes
        // if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
        //     document.body.classList.toggle('sb-sidenav-toggled');
        // }
        sidebarToggle.addEventListener('click', event => {
            event.preventDefault();
            document.body.classList.toggle('sb-sidenav-toggled');
            localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sb-sidenav-toggled'));
        });
    }

    const datatablesSimple = document.getElementById('datatablesSimple');
    if (datatablesSimple) {
        new simpleDatatables.DataTable(datatablesSimple);
    }

    const chatLogsModal = document.getElementById('chatLogsModal');
    const chatLogsContainer = document.getElementById('chatLogsContainer');
    if (chatLogsModal) {
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
    }



    const contactModal = document.getElementById('contactModal');

    if (contactModal) {
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
    }

    const departmentTablePanel = document.getElementById('departmentTablePanel');
    const employeeDetailsPanel = document.getElementById('employeeDetailsPanel');
    const employeeCards = document.querySelectorAll('.employee-details-card');

    document.querySelectorAll('.btn-count').forEach(button => {
        button.addEventListener('click', function () {
            const departmentId = button.getAttribute('data-department-id');

            // 모든 카드 숨기기
            employeeCards.forEach(card => {
                card.style.display = 'none';
            });

            // 해당 부서 ID에 해당하는 카드만 표시
            const targetCard = document.querySelector(`.employee-details-card[data-department-id="${departmentId}"]`);
            if (targetCard) {
                targetCard.style.display = 'block';
            }

            // 부서 정보 패널과 직원 정보 패널의 애니메이션 동기화
            departmentTablePanel.classList.add('shrink');
            employeeDetailsPanel.classList.add('expand');
        });
    });

    if (employeeDetailsPanel) {
        // 이벤트 위임 방식으로 닫기 버튼 처리
        employeeDetailsPanel.addEventListener('click', function (event) {
            if (event.target.matches('.close-btn')) {
                departmentTablePanel.classList.remove('shrink');
                employeeDetailsPanel.classList.remove('expand');

                // 모든 카드 숨기기
                employeeCards.forEach(card => {
                    card.style.display = 'none';
                });
            }
        });
    }


});
