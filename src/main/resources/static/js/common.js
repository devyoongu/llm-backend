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

    // 부서 저장 버튼 클릭 이벤트

    const departmentModal = document.getElementById('departmentModal');
    if (departmentModal) {
        // 저장 버튼 이벤트
        const saveDepartmentBtn = document.getElementById('saveDepartmentBtn');
        if (saveDepartmentBtn) {
            saveDepartmentBtn.addEventListener('click', function() {
                const departmentName = document.getElementById('departmentName').value;
                const mainPhone = document.getElementById('mainPhone').value;
                const parentId = document.getElementById('parentId').value;
                const depth = document.getElementById('depth').value;
                const jobNamesStr = document.getElementById('jobNames').value;

                if (!departmentName) {
                    alert('부서명은 필수입니다.');
                    return;
                }

                const requestData = {
                    departmentName: departmentName,
                    mainPhone: mainPhone,
                    parentId: parentId ? parseInt(parentId) : null,
                    depth: parseInt(depth) || 0,
                    jobNames: jobNamesStr ? jobNamesStr.split(',').map(job => job.trim()) : []
                };

                fetch('/api/department', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(requestData)
                })
                .then(response => response.json())
                .then(data => {
                    if (data.code === 'SUCCESS') {
                        alert('부서가 성공적으로 추가되었습니다.');
                        // 모든 입력 필드의 포커스 해제
                        document.activeElement.blur();
                        // 폼 초기화
                        document.getElementById('departmentForm').reset();
                        // Bootstrap의 방식으로 모달 닫기
                        const bsModal = bootstrap.Modal.getInstance(departmentModal);
                        if (bsModal) {
                            bsModal.hide();
                            setTimeout(() => {
                                window.location.reload();
                            }, 100);
                        }
                    } else {
                        alert('부서 추가에 실패했습니다: ' + data.message);
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('부서 추가 중 오류가 발생했습니다.');
                });
            });
        }

        // 모달이 열릴 때 이벤트
        departmentModal.addEventListener('show.bs.modal', function () {
            // 이전 입력값 초기화
            document.getElementById('departmentForm').reset();
        });

        // 모달이 닫히기 전 이벤트
        departmentModal.addEventListener('hide.bs.modal', function () {
            // 활성화된 요소의 포커스 해제
            document.activeElement.blur();
        });

        // 모달이 닫힌 후 이벤트
        departmentModal.addEventListener('hidden.bs.modal', function () {
            document.getElementById('departmentForm').reset();
        });
    }

    // 직원 추가 모달 관련 코드
    const employeeModal = document.getElementById('employeeModal');
    if (employeeModal) {
        employeeModal.addEventListener('show.bs.modal', function (event) {
            const button = event.relatedTarget;
            const departmentId = button.getAttribute('data-department-id');
            const departmentName = button.getAttribute('data-department-name');
            
            document.getElementById('employeeDepartmentId').value = departmentId;
            document.getElementById('employeeDepartmentName').value = departmentName;
        });

        // 저장 버튼 이벤트
        const saveEmployeeBtn = document.getElementById('saveEmployeeBtn');
        if (saveEmployeeBtn) {
            saveEmployeeBtn.addEventListener('click', function() {
                const departmentId = document.getElementById('employeeDepartmentId').value;
                const userId = document.getElementById('userId').value;
                const employeeName = document.getElementById('employeeName').value;
                const extensionNumber = document.getElementById('extensionNumber').value;
                const personalPhone = document.getElementById('personalPhone').value;
                const jobNamesStr = document.getElementById('employeeJobNames').value;
                const regionsStr = document.getElementById('regions').value;

                if (!employeeName || !userId) {
                    alert('직원명과 사용자 ID는 필수입니다.');
                    return;
                }

                const requestData = {
                    departmentId: parseInt(departmentId),
                    userId: userId,
                    employeeName: employeeName,
                    extensionNumber: extensionNumber,
                    personalPhone: personalPhone,
                    jobNames: jobNamesStr ? jobNamesStr.split(',').map(job => job.trim()) : [],
                    regions: regionsStr ? regionsStr.split(',').map(region => region.trim()) : []
                };

                fetch('/api/employee', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                    },
                    body: JSON.stringify(requestData)
                })
                .then(response => response.json())
                .then(data => {
                    if (data.code === 'SUCCESS') {
                        alert('직원이 성공적으로 추가되었습니다.');
                        document.activeElement.blur();
                        document.getElementById('employeeForm').reset();
                        const bsModal = bootstrap.Modal.getInstance(employeeModal);
                        if (bsModal) {
                            bsModal.hide();
                            setTimeout(() => {
                                window.location.reload();
                            }, 100);
                        }
                    } else {
                        alert('직원 추가에 실패했습니다: ' + data.message);
                    }
                })
                .catch(error => {
                    console.error('Error:', error);
                    alert('직원 추가 중 오류가 발생했습니다.');
                });
            });
        }

        // 모달이 닫힐 때 폼 초기화
        employeeModal.addEventListener('hidden.bs.modal', function () {
            document.getElementById('employeeForm').reset();
        });
    }

});
