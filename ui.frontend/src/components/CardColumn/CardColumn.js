import React from 'react';
import PropTypes from 'prop-types';
import { MapTo } from '@adobe/aem-react-editable-components';
import './CardColumn.css'; // We'll add styles here next

export const CardColumnEditConfig = {
  emptyLabel: 'Card Column',
  isEmpty: function (props) {
    return !props || !props.cards || props.cards.length === 0;
  }
};

const CardColumn = (props) => {
  if (CardColumnEditConfig.isEmpty(props)) {
    return null;
  }

  return (
    <div className="card-column" data-cq-path={props.cqPath}>
      <div className="card-grid">
        {props.cards.map((card, index) => (
          <div key={index} className="card">
            {card.image && (
              <div className="card-image">
                <img src={card.image} alt={card.title || `Card ${index + 1}`} />
              </div>
            )}
            <div className="card-content">
              {card.title && <h3>{card.title}</h3>}
              {card.description && <p>{card.description}</p>}
            </div>
          </div>
        ))}
      </div>
    </div>
  );
};

CardColumn.propTypes = {
  cards: PropTypes.arrayOf(
    PropTypes.shape({
      title: PropTypes.string,
      description: PropTypes.string,
      image: PropTypes.string
    })
  ),
  cqPath: PropTypes.string
};

MapTo('wknd-spa-react/components/cardcolumns')(CardColumn, CardColumnEditConfig);

export default CardColumn;
