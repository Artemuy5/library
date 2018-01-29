var baseUrl = $('#baseUrl').attr('content');

$(function() {
   $('.toog_butt').append('<span></span>');
    $('.block_comments span').each(function() {
       var btn = $(this),
           el = btn.parent().next('.comments_window');
        btn.on('click', function() {
           el.slideToggle().addClass('scroll_div').removeClass('hidden');

            var scroll = $(".scroll_div");
            scroll.scrollTop(scroll.prop('scrollHeight'));
        });
    });
});

function leaveComment(bookId) {
    $('#comment-link-' + bookId).removeClass('show');
    $('#comment-link-' + bookId).addClass('hidden');

    $('#comment-' + bookId).removeClass('hidden');
    $('#comment-' + bookId).addClass('show');
}

function sendComment(bookId) {
    var text = $('#comment-' + bookId + ' > textarea').val();
    $.ajax({
        url: baseUrl + 'comment/add',
        method: 'POST',
        contentType: 'application/json',
        data: JSON.stringify({
            bookId: bookId,
            text: text
        })
    }).done(function() {
        location.reload();
    });
}

function cancelComment(bookId) {
    $('#comment-link-' + bookId).removeClass('hidden');
    $('#comment-link-' + bookId).addClass('show');

    $('#comment-' + bookId).removeClass('show');
    $('#comment-' + bookId).addClass('hidden');
}


