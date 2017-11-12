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

// feed
function adjust_feed()
{
	window.addEventListener('resize', function(event){
		adjust();
	});
				
	adjust();
}
			
function adjust()
{
	var width = window.innerWidth;
	var feed_width;
	if (width < 481)
	{
		feed_width='100%';
		calcImageWidth(1.0);
	}
	else if (width < 1025)
	{
		feed_width='45%';
		calcImageWidth(0.45);
	}
	else {
	    feed_width='30%';
	    calcImageWidth(0.3);
	}

	var figures = document.getElementsByClassName("feed_figure");
	for (var i = 0; i < figures.length; i++) {
        var figure = figures[i];
        figure.style.width = feed_width;
    }
}

function calcImageWidth(widthScale)
{
    var blur_shop_height = Math.round(window.innerHeight/2);
    var header_image_url = 'https://www.img.sneakerate.com/blur_shop.jpg?w=' + document.body.clientWidth + '&h=' + blur_shop_height;
    header.style.backgroundImage = "url('" + header_image_url +"')";
}