// common.js
function lazyLoadCss()
{
	var loadDeferredStyles = function() {
            var addStylesNode = document.getElementById("deferred-styles");
            var replacement = document.createElement("div");
            replacement.innerHTML = addStylesNode.textContent;
            document.body.appendChild(replacement)
            addStylesNode.parentElement.removeChild(addStylesNode);
    };
    var raf = requestAnimationFrame || mozRequestAnimationFrame ||
        webkitRequestAnimationFrame || msRequestAnimationFrame;
    if (raf) raf(function() { window.setTimeout(loadDeferredStyles, 0); });
    else window.addEventListener('load', loadDeferredStyles);
}

// story
function on_load()
{
	window.addEventListener('resize', function(event){
		resizeImages();
		initYoutubeVideos();
	});

	resizeImages();
    initYoutubeVideos();
}

function resizeImages()
{
    var width = window.innerWidth * 0.3;
    var images = document.getElementsByClassName("resized_image");
    for (var i = 0; i < images.length; i++) {
        var image = images[i];
        image.src = image.name + '?w=' + width;
        image.hidden = false;
    }

    var blur_shop_height = Math.round(window.innerHeight/2);
    var header_image_url = 'https://www.img.sneakerate.com/blur_shop.jpg?w=' + document.body.clientWidth + '&h=' + blur_shop_height;
    header.style.backgroundImage = "url('" + header_image_url +"')";
}

// Youtube
function initYoutubeVideos() {
    var thumbQuality;
    var videoWidth;
    var videoHeight;

    var maxWidth = window.innerWidth / 2;
    if (maxWidth >= 1280) {
        thumbQuality = "maxresdefault.jpg";
        videoWidth = 1280;
        videoHeight = 720;
    } else if (maxWidth >= 640) {
        thumbQuality = "sddefault.jpg";
        videoWidth = 640;
        videoHeight = 480;
    } else if (maxWidth >= 480) {
        thumbQuality = "hqdefault.jpg";
        videoWidth = 480;
        videoHeight = 360;
    } else if (maxWidth >= 320) {
        thumbQuality = "mqdefault.jpg";
        videoWidth = 320;
        videoHeight = 180;
    } else {
        thumbQuality = "default.jpg";
        videoWidth = 120;
        videoHeight = 90;
    }

    var videos = document.getElementsByClassName("youtube_video");
    for (var i = 0; i < videos.length; i++) {
        var video = videos[i];
        initVideo(video, thumbQuality, videoWidth, videoHeight);
    }
}
    
function initVideo(video, thumbQuality, videoWidth, videoHeight)
{
    video.style.width = videoWidth + "px";
    video.style.height = videoHeight + "px";

    var thumb = document.createElement("img");
    var thumb_src = "https://i.ytimg.com/vi/" + video.getAttribute("video_id") + "/" + thumbQuality;
    thumb.setAttribute("src", thumb_src);
    thumb.onclick = function() { on_video_click(video); };

    var play = document.createElement("div");
    play.className = "play";
    var marginLeft = videoWidth / 2 - 36;
    var marginTop = videoHeight / 2 - 36;
    play.style.marginLeft = marginLeft + "px";
    play.style.marginTop = marginTop + "px";
    play.onclick = function() { on_video_click(video); };

    while (video.hasChildNodes()) {
        video.removeChild(video.lastChild);
    }

    video.appendChild(play);
    video.appendChild(thumb);
}

function on_video_click(video)
{
    var iframe = document.createElement("iframe");
    iframe.setAttribute("frameborder", "0");
    iframe.setAttribute("allowfullscreen", "1");
    var video_url = "https://www.youtube.com/embed/" + video.getAttribute("video_id") + "?autoplay=1";
    iframe.setAttribute("src", video_url);
    video.removeChild(video.lastChild);
    video.removeChild(video.lastChild);
    video.appendChild(iframe);
}