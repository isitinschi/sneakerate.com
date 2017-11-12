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

// trends
function on_load()
{
	window.addEventListener('resize', function(event){
		calcImageWidth();
		initAmazon();
	});

	calcImageWidth();
	initAmazon();
}

function calcImageWidth()
{
    var width = window.innerWidth;
    var height = window.innerHeight;

    var targetMainImageWidth = document.body.clientWidth;
    targetMainImageWidth *= 0.3;
    if (targetMainImageWidth > 1024)
    {
        targetMainImageWidth = 1024;
    }
    targetMainImageWidth = Math.round(targetMainImageWidth);

    var blur_shop_height = Math.round(height/4);
    var header_image_url = 'https://www.img.sneakerate.com/blur_shop.jpg?w=' + document.body.clientWidth + '&h=' + blur_shop_height;
    header.style.backgroundImage = "url('" + header_image_url +"')";

    if (targetMainImageWidth <= 500)
    {
        targetMainImageWidth = 500;
    }
    else
    {
        targetMainImageWidth = 1000;
    }

    var images = main.getElementsByClassName("shoe_image");
    for (var i = 0; i < images.length; i++) {
        var image = images[i];
        image.src = "https://s3-us-west-2.amazonaws.com/sneakerate/" + image.name + '-' + targetMainImageWidth + ".jpg";
        image.hidden = false;
    }
}

function initAmazon() {
    var width = window.innerWidth;
    var height = window.innerHeight;

    var logoWidth = document.body.clientWidth;
    logoWidth *= 0.3;

    logoWidth = Math.round(logoWidth);

    var amazon_logos = main.getElementsByClassName("amazon_checkout_logo");
    for (var i = 0; i < amazon_logos.length; i++) {
        amazon_logos[i].setAttribute("src", "https://www.img.sneakerate.com/amazon-checkout.png?w=" + logoWidth);
        amazon_logos[i].hidden = false;
    }
}