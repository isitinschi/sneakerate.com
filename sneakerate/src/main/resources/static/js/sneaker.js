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

//on_vote
function on_vote(figure)
{
	figure.getElementsByClassName("vote_main")[0].style.opacity=0.3;
	figure.getElementsByClassName("vote_block")[0].style.display='none';
	figure.getElementsByClassName("thank_you")[0].style.display='block';
}

// sneaker
function on_load()
{
	window.addEventListener('resize', function(event){
		adjust_width();
		init_image_groups();
		initVideo();
		initAmazon();
	});

    calcImageWidth()
	adjust_width();
	init_image_groups();
	initVideo();
	initAmazon();
}

function calcImageWidth()
{
    var blur_shop_height = Math.round(window.innerHeight/2);
    var header_image_url = 'https://www.img.sneakerate.com/blur_shop.jpg?w=' + document.body.clientWidth + '&h=' + blur_shop_height;
    header.style.backgroundImage = "url('" + header_image_url +"')";
}

function adjust_width()
{
	var width = window.innerWidth;
	var height = window.innerHeight;

    if (width >= height && width >= 1024) // do we have sidebar?
    {
//        sidebar.style.display='inline-block';
        shoes_block.style.width = '75%';
    }
    else
    {
//        sidebar.style.display='none';
        shoes_block.style.width = '100%';
    }
}

function on_change_image_group(image_group)
{
    var group = image_group.getAttribute("image_group");
    var image_views = shoes_block.getElementsByClassName("image_view");
    for (var i = 0; i < image_views.length; i++) {
        var image_view = image_views[i];
        if (image_view.getAttribute("image_group") == group) {
            image_view.parentElement.style.display='block';
            image_view.src=image_view.getAttribute("data-src");
            if (image_view.getAttribute("image_view") == 0)
            {
                on_focus_image_view(image_view);
            }
        } else {
            image_view.parentElement.style.display='none';
        }
    }
}

function on_focus_image_view(image_view)
{
    var targetMainImageWidth = window.innerWidth;
    targetMainImageWidth *= 0.5;
    if (targetMainImageWidth > 1024)
    {
        targetMainImageWidth = 1024;
    }
    targetMainImageWidth *= 0.8;

    targetMainImageWidth = Math.round(targetMainImageWidth);

    main_image_view.src='https://www.img.sneakerate.com/' + image_view.name + '.jpg?w=' + targetMainImageWidth;
    main_image_view.hidden = false;
}

function init_image_views(group)
{
    var image_views = shoes_block.getElementsByClassName("image_view");
    var size = 0;
    for (var i = 0; i < image_views.length; i++) {
        var image_view = image_views[i];
        if (image_view.getAttribute("image_group") == group)
        {
            size = size + 1;
            image_view.parentElement.style.display='block';
            if (image_view.getAttribute("image_view") == 0)
            {
                image_view.style.borderColor='orange';
                on_focus_image_view(image_view);
            }
        }
        else
        {
            image_view.parentElement.style.display='none';
        }
    }

    var imageViewWidth = window.innerWidth;
    imageViewWidth *= 0.5;
    if (imageViewWidth > 1024)
    {
        imageViewWidth = 1024;
    }
    imageViewWidth *= 0.8;
    imageViewWidth *= 1.0 / size;
    imageViewWidth = Math.round(imageViewWidth);

    for (var i = 0; i < image_views.length; i++) {
        var image_view = image_views[i];
        if (image_view.getAttribute("image_group") == group)
        {
            image_view.style.maxHeight = (100 / size) + "%";
            image_view.src = 'https://www.img.sneakerate.com/' + image_view.name + '.jpg?w=' + imageViewWidth;
        }
    }
}

function init_image_groups()
{
    var image_groups = shoes_block.getElementsByClassName("image_group");
    var size = image_groups.length;

    var imageGroupWidth = window.innerWidth;
    imageGroupWidth *= 0.5;
    if (imageGroupWidth > 1024)
    {
       imageGroupWidth = 1024;
    }
    imageGroupWidth *= 0.8;
    imageGroupWidth *= 1.0 / size;
    imageGroupWidth = Math.round(imageGroupWidth);

    for (var i = 0; i < image_groups.length; i++) {
        var image_group = image_groups[i];
        if (image_group.getAttribute("image_group") == 0)
        {
            image_group.style.borderColor='orange';
        }
        image_group.style.maxWidth = (100 / size) + "%";
        image_group.src = 'https://www.img.sneakerate.com/' + image_group.name + '.jpg?w=' + imageGroupWidth;
    }

    init_image_views(0);
}

function initAmazon() {
    var logoWidth = window.innerWidth;
    logoWidth *= 0.5;
    if (logoWidth > 1024)
    {
        logoWidth = 1024;
    }
    logoWidth *= 0.8;
    logoWidth *= 0.25;

    logoWidth = Math.round(logoWidth);

    amazon_logo.setAttribute("src", "https://www.img.sneakerate.com/amazon-logo.png?w=" + logoWidth);
    amazon_logo.hidden = false;
}

function on_mouse_in_view(img) {
    var image_views = shoes_block.getElementsByClassName("image_view");
    for (var i = 0; i < image_views.length; i++) {
        var image_view = image_views[i];
        image_view.style.borderColor = 'rgb(200, 200, 200)';
    }
    img.style.borderColor='orange';
}

function on_mouse_in_group(ig) {
    var image_groups = shoes_block.getElementsByClassName("image_group");
    for (var i = 0; i < image_groups.length; i++) {
        var image_group = image_groups[i];
        image_group.style.borderColor = 'rgb(200, 200, 200)';
    }
    ig.style.borderColor='orange';

    var group = ig.getAttribute("image_group");
    init_image_views(group);

    amazon_link.href = ig.getAttribute("amazon");
}

// Youtube
function initVideo() {
    var thumbQuality;
    var shoeVideoWidth;
    var shoeVideoHeight;

    var maxWidth = window.innerWidth * 0.5;
    if (maxWidth >= 1280) {
        thumbQuality = "maxresdefault.jpg";
        shoeVideoWidth = 1280;
        shoeVideoHeight = 720;
    } else if (maxWidth >= 640) {
        thumbQuality = "sddefault.jpg";
        shoeVideoWidth = 640;
        shoeVideoHeight = 480;
    } else if (maxWidth >= 480) {
        thumbQuality = "hqdefault.jpg";
        shoeVideoWidth = 480;
        shoeVideoHeight = 360;
    } else if (maxWidth >= 320) {
        thumbQuality = "mqdefault.jpg";
        shoeVideoWidth = 320;
        shoeVideoHeight = 180;
    } else {
        thumbQuality = "default.jpg";
        shoeVideoWidth = 120;
        shoeVideoHeight = 90;
    }

    shoe_video.style.width = shoeVideoWidth + "px";
    shoe_video.style.height = shoeVideoHeight + "px";

    var thumb = document.createElement("img");
    var thumb_src = "https://i.ytimg.com/vi/" + shoe_video.getAttribute("video_id") + "/" + thumbQuality;
    thumb.setAttribute("src", thumb_src);
    thumb.onclick = function() { on_video_click(); };

    var play = document.createElement("div");
    play.className = "play";
    var marginLeft = shoeVideoWidth / 2 - 36;
    var marginTop = shoeVideoHeight / 2 - 36;
    play.style.marginLeft = marginLeft + "px";
    play.style.marginTop = marginTop + "px";
    play.onclick = function() { on_video_click(); };

    while (shoe_video.hasChildNodes()) {
        shoe_video.removeChild(shoe_video.lastChild);
    }

    shoe_video.appendChild(play);
    shoe_video.appendChild(thumb);
}

function on_video_click() {
    var iframe = document.createElement("iframe");
    iframe.setAttribute("frameborder", "0");
    iframe.setAttribute("allowfullscreen", "1");
    var video_url = "https://www.youtube.com/embed/" + shoe_video.getAttribute("video_id") + "?autoplay=1";
    iframe.setAttribute("src", video_url);
    shoe_video.removeChild(shoe_video.lastChild);
    shoe_video.removeChild(shoe_video.lastChild);
    shoe_video.appendChild(iframe);
}