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

// on_vote
function on_vote(figure)
{
	figure.getElementsByClassName("vote_main")[0].style.opacity=0.3;
	figure.getElementsByClassName("vote_block")[0].style.display='none';
	figure.getElementsByClassName("thank_you")[0].style.display='block';
}

// trends
function on_load()
{
	window.addEventListener('resize', function(event){
		adjust_width();
		initAmazon();
	});

	adjust_width();
	initAmazon();
}

function adjust_width()
{
	var width = window.innerWidth;
	var height = window.innerHeight;

    if (width >= height && width >= 1024) // do we have sidebar?
    {
        sidebar.style.display='inline-block';
        shoes_block.style.width = '75%';
    }
    else
    {
        sidebar.style.display='none';
        shoes_block.style.width = '100%';
    }

    calcImageWidth();
}

function calcImageWidth()
{
    var width = window.innerWidth;
    var height = window.innerHeight;
    var sidebarExists = width >= height && width >= 1024;

    var targetMainImageWidth = document.body.clientWidth;
    if (sidebarExists)
    {
        targetMainImageWidth *= 0.75;
    }
    targetMainImageWidth *= 0.6;
    if (targetMainImageWidth > 1024)
    {
        targetMainImageWidth = 1024;
    }
    targetMainImageWidth = Math.round(targetMainImageWidth);

    var blur_shop_height = Math.round(height/2);
    var header_image_url = 'https://www.img.sneakerate.com/blur_shop.jpg?w=' + document.body.clientWidth + '&h=' + blur_shop_height;
    header.style.backgroundImage = "url('" + header_image_url +"')";

    var images = shoes_block.getElementsByClassName("shoe_image");
    for (var i = 0; i < images.length; i++) {
        var image = images[i];
        image.src = 'https://www.img.sneakerate.com/' + image.name + '?w=' + targetMainImageWidth;
        image.hidden = false;
    }

    if (sidebarExists)
    {
        var targetSidebarImageWidth = document.body.clientWidth;
        targetSidebarImageWidth *= 0.25;
        if (targetSidebarImageWidth > 512)
        {
            targetSidebarImageWidth = 512;
        }
        targetSidebarImageWidth *= 0.82;
        targetSidebarImageWidth = Math.round(targetSidebarImageWidth);

        images = sidebar.getElementsByClassName("shoe_image");
            for (var i = 0; i < images.length; i++) {
                var image = images[i];
                image.src = 'https://www.img.sneakerate.com/' + image.name + '?w=' + targetSidebarImageWidth;
            }
    }
}

function initAmazon() {
    var width = window.innerWidth;
    var height = window.innerHeight;
    var sidebarExists = width >= height && width >= 1024;

    var logoWidth = document.body.clientWidth;
    if (sidebarExists)
    {
        logoWidth *= 0.75;
    }
    logoWidth *= 0.6;
    if (logoWidth > 1024)
    {
        logoWidth = 1024;
    }
    logoWidth *= 0.94;
    logoWidth *= 0.25;

    logoWidth = Math.round(logoWidth);

    var amazon_logos = shoes_block.getElementsByClassName("amazon_logo");
    for (var i = 0; i < amazon_logos.length; i++) {
        amazon_logos[i].setAttribute("src", "https://www.img.sneakerate.com/amazon-logo.png?w=" + logoWidth);
        amazon_logos[i].hidden = false;
    }
}