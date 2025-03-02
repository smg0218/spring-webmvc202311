<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판 글쓰기</title>


    <%@include file="../include/static-head.jsp" %>

    <style>


        .form-container {
            width: 500px;
            margin: auto;
            padding: 20px;
            background-image: linear-gradient(135deg, #a1c4fd, #fbc2eb);
            box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
            border-radius: 4px;
            font-size: 18px;
        }

        .form-container h1 {
            font-size: 40px;
            font-weight: 700;
            letter-spacing: 10px;
            text-align: center;
            margin-bottom: 20px;
            color: #ffffff;
        }

        .form-container h2 {
            font-size: 30px;
            color: #222;
            text-align: center;
            margin-bottom: 20px;
        }

        label {
            display: block;
            margin-bottom: 5px;
            font-size: 20px;
        }

        #title, #writer {
            font-size: 18px;
            width: 100%;
            padding: 8px;
            box-sizing: border-box;
            border: 2px solid #ffffff;
            border-radius: 8px;
            margin-bottom: 10px;
            background-color: rgba(255, 255, 255, 0.8);
        }

        #content {
            height: 400px;
            font-size: 18px;
            width: 100%;
            padding: 8px;
            box-sizing: border-box;
            border: 2px solid #ffffff;
            border-radius: 8px;
            margin-bottom: 10px;
            background-color: rgba(255, 255, 255, 0.8);
        }

        textarea {
            resize: none;
            height: 200px;
        }

        .buttons {
            display: flex;
            justify-content: flex-end;
            margin-top: 20px;
        }

        button {
            font-size: 20px;
            padding: 10px 20px;
            border: none;
            margin-right: 10px;
            background-color: #4CAF50;
            color: white;
            cursor: pointer;
            border-radius: 4px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
            transition: background-color 0.3s;
        }

        button.list-btn {
            background: #e61e8c;
        }

        button:hover {
            background-color: #3d8b40;
        }

        button.list-btn:hover {
            background: #e61e8c93;
        }

        /* 페이지 css */
        /* 페이지 액티브 기능 */
        .pagination .page-item.p-active a {
            background: #333 !important;
            color: #fff !important;
            cursor: default;
            pointer-events: none;
        }

        .pagination .page-item:hover a {
            background: #888 !important;
            color: #fff !important;
        }
        /* 댓글 프로필 */
        .profile-box {
            width: 70px;
            height: 70px;
            border-radius: 50%;
            overflow: hidden;
            margin: 10px auto;
        }
        .profile-box img {
            width: 100%;
        }

        .reply-profile {
            width: 30px;
            height: 30px;
            border-radius: 50%;
            margin-right: 10px;

        }
    </style>
</head>
<body>

<%@include file="../include/header.jsp" %>

<div id="wrap" class="form-container">
    <h1>${b.boardNo}번 게시물 내용~ </h1>
    <h2># 작성일자: ${b.date}</h2>

    <label for="writer">작성자</label>
    <input type="text" id="writer" name="writer" value="${b.writer}" readonly>

    <label for="title">제목</label>
    <input type="text" id="title" name="title" value="${b.title}" readonly>

    <label for="content">내용</label>
    <div id="content">${b.content}</div>
    <div class="buttons">
        <button class="list-btn" type="button"
                onclick="window.location.href='/board/list?pageNo=${s.pageNo}&amount=${s.amount}&type=${s.type}&keyword=${s.keyword}'">
            목록
        </button>
    </div>

    <!-- 댓글 영역 -->

    <div id="replies" class="row">
        <div class="offset-md-1 col-md-10">
            <!-- 댓글 쓰기 영역 -->
            <div class="card">
                <div class="card-body">

                    <c:if test="${empty login}">
                        <a href="/members/sign-in">댓글은 로그인 후 작성해주세요!!</a>
                    </c:if>

                    <c:if test="${not empty login}">
                        <div class="row">
                            <div class="col-md-9">
                                <div class="form-group">
                                    <label for="newReplyText" hidden>댓글 내용</label>
                                    <textarea rows="3" id="newReplyText" name="replyText" class="form-control"
                                              placeholder="댓글을 입력해주세요."></textarea>
                                </div>
                            </div>
                            <div class="col-md-3">
                                <div class="form-group">

                                    <div class="profile-box">
                                        <c:choose>
                                            <c:when test="${login.profile != null}">
                                                <img src="/local${login.profile}" alt="프사">
                                            </c:when>
                                            <c:otherwise>
                                                <img src="/assets/img/anonymous.jpg" alt="프사">
                                            </c:otherwise>
                                        </c:choose>
                                    </div>

                                    <label for="newReplyWriter" hidden>댓글 작성자</label>
                                    <input id="newReplyWriter" name="replyWriter" type="text"
                                           class="form-control" placeholder="작성자 이름"
                                           style="margin-bottom: 6px;" value="${login.nickName}" readonly>
                                    <button id="replyAddBtn" type="button"
                                            class="btn btn-dark form-control">등록
                                    </button>
                                </div>
                            </div>
                        </div>
                    </c:if>

                </div>
            </div> <!-- end reply write -->

            <!--댓글 내용 영역-->
            <div class="card">
                <!-- 댓글 내용 헤더 -->
                <div class="card-header text-white m-0" style="background: #343A40;">
                    <div class="float-left">댓글 (<span id="replyCnt">0</span>)</div>
                </div>

                <!-- 댓글 내용 바디 -->
                <div id="replyCollapse" class="card">
                    <div id="replyData">
                        <!--
                        < JS로 댓글 정보 DIV삽입 >
                    -->
                    </div>

                    <!-- 댓글 페이징 영역 -->
                    <ul class="pagination justify-content-center">
                        <!--
                        < JS로 댓글 페이징 DIV삽입 >
                    -->
                    </ul>
                </div>
            </div> <!-- end reply content -->
        </div>
    </div> <!-- end replies row -->

    <!-- 댓글 수정 모달 -->
    <div class="modal fade bd-example-modal-lg" id="replyModifyModal">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">

                <!-- Modal Header -->
                <div class="modal-header" style="background: #343A40; color: white;">
                    <h4 class="modal-title">댓글 수정하기</h4>
                    <button type="button" class="close text-white" data-bs-dismiss="modal">X</button>
                </div>

                <!-- Modal body -->
                <div class="modal-body">
                    <div class="form-group">
                        <input id="modReplyId" type="hidden">
                        <label for="modReplyText" hidden>댓글내용</label>
                        <textarea id="modReplyText" class="form-control" placeholder="수정할 댓글 내용을 입력하세요."
                                  rows="3"></textarea>
                    </div>
                </div>

                <!-- Modal footer -->
                <div class="modal-footer">
                    <button id="replyModBtn" type="button" class="btn btn-dark">수정</button>
                    <button id="modal-close" type="button" class="btn btn-danger"
                            data-bs-dismiss="modal">닫기
                    </button>
                </div>
            </div>
        </div>
    </div>

    <!-- end replyModifyModal -->


</div>

<script>
    const URL = '/api/v1/replies';
    const bno = '${b.boardNo}';
    const currentAccount = '${login.account}'; // 로그인한 사람 계정
    const auth = '${login.auth}'; // 로그인한 사람 권한

    // 댓글 관련 비동기통신(AJAX) 스크립트

    // 화면에 페이지들을 렌더링하는 함수
    function renderPage({
                            begin, end, prev, next, page, finalPage
                        }) {

        let tag = "";

        //이전 버튼 만들기
        if (prev) {
            tag += `<li class='page-item'><a class='page-link page-active' href='\${begin - 1}'>이전</a></li>`;
        }
        //페이지 번호 리스트 만들기
        for (let i = begin; i <= end; i++) {
            let active = '';
            if (page.pageNo === i) {
                active = 'p-active';
            }

            tag += `<li class='page-item \${active}'><a class='page-link page-custom' href='\${i}'>\${i}</a></li>`;
        }
        //다음 버튼 만들기
        if (next) {
            tag += `<li class='page-item'><a class='page-link page-active' href='\${end + 1}'>다음</a></li>`;
        }

        // 페이지태그 렌더링
        const $pageUl = document.querySelector('.pagination');
        $pageUl.innerHTML = tag;

        // ul에 마지막페이지 번호 저장.
        $pageUl.dataset.fp = finalPage;

    }


    // 화면에 댓글 태그들을 렌더링하는 함수
    function renderReplies({replies, count, pageInfo}) {

        let tag = '';

        if (replies !== null && replies.length > 0) {
            for (let reply of replies) {

                const {rno, writer, text, regDate, account, profile} = reply;

                tag += `
        <div id='replyContent' class='card-body' data-replyId='\${rno}'>
            <div class='row user-block'>
                <span class='col-md-8'>
                `;
                tag += (profile
                    ? `<img class="reply-profile" src="/local\${profile}" alt="profile image">`
                    : `<img class="reply-profile" src="/assets/img/anonymous.jpg" alt="anonymous image">`);
                tag += `<b>\${writer}</b>
                </span>
                <span class='col-md-4 text-right'><b>\${regDate}</b></span>
            </div><br>
            <div class='row'>
                <div class='col-md-9'>\${text}</div>
                <div class='col-md-3 text-right'>
            `;

                if (auth === 'ADMIN' || currentAccount === account) {
                    tag += `
                    <a id='replyModBtn' class='btn btn-sm btn-outline-dark' data-bs-toggle='modal' data-bs-target='#replyModifyModal'>수정</a>&nbsp;
                    <a id='replyDelBtn' class='btn btn-sm btn-outline-dark' href='#'>삭제</a>
            `;
                }


                tag += `   </div>
            </div>
        </div>
      `;


            } //end for

        }// end if
        else {
            tag += "<div id='replyContent' class='card-body'>댓글이 아직 없습니다! ㅠㅠ</div>";
        }

        // 댓글 수 렌더링
        document.getElementById('replyCnt').innerHTML = count;
        // 댓글 렌더링
        document.getElementById('replyData').innerHTML = tag;

        // 페이지 렌더링
        renderPage(pageInfo);
    }

    // 서버에 실시간으로 비동기통신을 해서 JSON을 받아오는 함수
    function fetchGetReplies(page = 1) {

        fetch(`\${URL}/\${bno}/page/\${page}`)
            .then(res => res.json())
            .then(replyList => {
                console.log(replyList);
                renderReplies(replyList);
            })
        ;
    }

    // 페이지 클릭 이벤트 핸들러 등록 함수
    function makePageButtonClickEvent() {

        const $pageUl = document.querySelector('.pagination');

        $pageUl.onclick = e => {

            // 이벤트 타겟이 a링크가 아닌경우 href속성을 못가져올 수 있으니 타겟 제한하기
            if (!e.target.matches('.page-item a')) return;

            // console.log(e.target.getAttribute('href'));

            e.preventDefault(); // href 링크이동 기능 중단 : 태그 기본 기능 동작 중단

            // 페이지 번호에 맞는 새로운 댓글 목록 비동기 요청
            fetchGetReplies(e.target.getAttribute('href'));
        };

    }

    // 댓글 등록 처리 핸들러 등록 함수
    function makeReplyPostClickEvent() {
        const $addBtn = document.getElementById('replyAddBtn');

        $addBtn.onclick = e => {

            const $replyText = document.getElementById('newReplyText');
            const $replyWriter = document.getElementById('newReplyWriter');

            // console.log($replyText.value);
            // console.log($replyWriter.value);

            const textVal = $replyText.value.trim();
            const writerVal = $replyWriter.value.trim();

            // 사용자 입력값 검증
            if (textVal === '') {
                alert('댓글 내용은 필수값입니다!!');
                return;
            } else if (writerVal === '') {
                alert('댓글 작성자는 필수값입니다!!');
                return;
            } else if (writerVal.length < 2 || writerVal.length > 8) {
                alert('댓글 작성자는 2글자에서 8글자 사이로 작성하세요!');
                return;
            }


            // 서버로 보낼 데이터
            const payload = {
                text: $replyText.value,
                author: $replyWriter.value,
                bno: bno
            };

            // GET방식을 제외한 요청의 정보 만들기
            const requestInfo = {
                method: 'POST',
                headers: {
                    'content-type': 'application/json'
                },
                body: JSON.stringify(payload)
            };
            // 서버에 POST 요청 보내기
            fetch(URL, requestInfo)
                .then(res => {
                    if (res.status === 200) {
                        alert('댓글이 정상 등록되었습니다!');
                        return res.json();
                    } else {
                        alert('댓글 등록에 실패했습니다!');
                        return res.text();
                    }
                })
                .then(responseData => {
                    // console.log(responseData);
                    // 입력창 비우고 새로운 목록 리렌더링
                    // $replyWriter.value = '';
                    $replyText.value = '';

                    fetchGetReplies(responseData.pageInfo.finalPage);
                });
        };

    }

    // 댓글 삭제 + 수정모드진입 이벤트 핸들러 등록 및 처리 함수
    function makeReplyRemoveClickEvent() {

        const $replyData = document.getElementById('replyData');

        $replyData.onclick = e => {

            e.preventDefault(); // a태그 링크이동 기능 중지

            // 댓글번호 찾기
            const rno = e.target.closest('#replyContent').dataset.replyid;

            // 삭제버튼에만 이벤트가 작동하도록 설정
            if (e.target.matches('#replyDelBtn')) {
                // console.log('삭제 버튼 클릭!');

                if (!confirm('정말 삭제할까요??')) return;

                // console.log(rno);

                const requestInfo = {
                    method: 'DELETE'
                };
                // 서버에 삭제 비동기 요청
                fetch(`\${URL}/\${rno}`, requestInfo)
                    .then(res => {
                        if (res.status === 200) {
                            alert('댓글이 삭제되었습니다!');
                            return res.json();
                        } else {
                            alert('댓글 삭제에 실패했습니다.');
                            return;
                        }
                    })
                    .then(responseResult => {
                        renderReplies(responseResult);
                    });
            } else if (e.target.matches('#replyModBtn')) {
                // console.log('수정모드 진입!');

                // 클릭한 수정버튼 근처에 있는 댓글내용 읽기
                const replyText = e.target.parentNode.previousElementSibling.textContent;
                // console.log(replyText);

                // 읽은 댓글 내용을 모달 바디에 집어넣기
                document.getElementById('modReplyText').value = replyText;

                // 읽은 댓글의 댓글번호를 모달에 집어넣기
                const $modal = document.querySelector('.modal');
                $modal.dataset.rno = rno;

            }

        };

    }

    // 댓글 수정 클릭 이벤트 처리 함수
    function makeReplyModifyClickEvent() {

        const $modBtn = document.getElementById('replyModBtn');

        $modBtn.addEventListener('click', e => {

            const payload = {
                rno: +document.querySelector('.modal').dataset.rno,
                text: document.getElementById('modReplyText').value,
                bno: +bno
            };
            console.log(payload);

            const requestInfo = {
                method: 'PUT',
                headers: {
                    'content-type': 'application/json'
                },
                body: JSON.stringify(payload)
            };

            fetch(URL, requestInfo)
                .then(res => {
                    if (res.status === 200) {
                        alert('댓글이 수정되었습니다.');
                        // modal창 닫기
                        document.getElementById('modal-close').click();
                        return res.json();
                    } else {
                        alert('댓글이 수정에 실패했습니다.');
                        document.getElementById('modal-close').click();
                        return;
                    }
                })
                .then(result => {
                    renderReplies(result);
                });
        });
    }

    //========== 메인 실행부 ==========//

    // 즉시 실행함수
    (() => {

        // 댓글 서버에서 불러오기
        fetchGetReplies();

        // 페이지 번호 클릭 이벤트 핸들러처리
        makePageButtonClickEvent();

        // 댓글 등록 클릭 이벤트 핸들러 처리
        makeReplyPostClickEvent();

        // 댓글 삭제 클릭 이벤트 핸들러 처리
        makeReplyRemoveClickEvent();

        // 댓글 수정 클릭 이벤트 핸들러 처리
        makeReplyModifyClickEvent();

    })();


</script>

</body>
</html>