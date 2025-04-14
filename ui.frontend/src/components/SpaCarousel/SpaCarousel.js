import React, { useEffect, useState } from 'react';
import PropTypes from 'prop-types';
import { MapTo } from '@adobe/aem-react-editable-components';
import './spaCarousel.css';

export const SpaCarouselEditConfig = {
  emptyLabel: 'Spa Carousel',

  isEmpty: function (props) {
    return !props || !props.slides || props.slides.length === 0;
  },
};

const SpaCarousel = (props) => {
  const { slides = [], cqPath } = props;
  const [currentIndex, setCurrentIndex] = useState(0);

  const goToSlide = (index) => {
    setCurrentIndex(index);
  };

  useEffect(() => {
    const interval = setInterval(() => {
      setCurrentIndex((prevIndex) => (prevIndex + 1) % slides.length);
    }, 5000);
    return () => clearInterval(interval);
  }, [slides.length]);

  if (SpaCarouselEditConfig.isEmpty(props)) {
    return null;
  }

  return (
    <div className="carousel-wrapper" data-cq-path={cqPath}>
      <div className="carousel-thumbnails">
        {slides.map((item, index) => (
          <img
            key={index}
            src={item.image}
            alt={item.caption}
            className={`thumbnail ${index === currentIndex ? 'active' : ''}`}
            onClick={() => goToSlide(index)}
          />
        ))}
      </div>

      <div className="carousel-main">
        <div className="carousel-slide">
          <img
            src={slides[currentIndex].image}
            alt={slides[currentIndex].caption}
          />
          <h3>{slides[currentIndex].caption}</h3>
        </div>
      </div>
    </div>
  );
};

SpaCarousel.propTypes = {
  slides: PropTypes.arrayOf(
    PropTypes.shape({
      image: PropTypes.string,
      caption: PropTypes.string,
    })
  ),
  cqPath: PropTypes.string,
};

MapTo('wknd-spa-react/components/spaCarousel')(SpaCarousel, SpaCarouselEditConfig);

export default SpaCarousel;
