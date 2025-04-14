import React from 'react';
import PropTypes from 'prop-types';
import {MapTo} from '@adobe/aem-react-editable-components';
import './spaCarousel.css';

// Define the edit config for AEM editor support
export const SpaCarouselEditConfig = {
  emptyLabel: 'Spa Carousel',

  isEmpty: function (props) {
    return !props || !props.text || props.text.length === 0;
  },
};

const SpaCarousel = (props) => {
  if (SpaCarouselEditConfig.isEmpty(props)) {
    return null;
  }

  const { items = [], cqPath } = props;

  return (
    <div className="carousel" data-cq-path={cqPath}>
      <div className="carousel-container">
        {items.map((item, index) => (
          <div key={index} className="carousel-slide">
            <img src={item.imageUrl} alt={item.alt || `Slide ${index + 1}`} />
            {item.title && <h3>{item.title}</h3>}
            {item.description && <p>{item.description}</p>}
          </div>
        ))}
      </div>
    </div>
  );
};

SpaCarousel.propTypes = {
  items: PropTypes.arrayOf(
    PropTypes.shape({
      imageUrl: PropTypes.string,
      alt: PropTypes.string,
      title: PropTypes.string,
      description: PropTypes.string,
    })
  ),
  cqPath: PropTypes.string,
};

// Map resourceType to React component with edit config
MapTo('wknd-spa-react/components/spaCarousel')(SpaCarousel, SpaCarouselEditConfig);

export default SpaCarousel;
