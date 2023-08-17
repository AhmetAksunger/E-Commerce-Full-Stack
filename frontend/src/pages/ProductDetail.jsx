import React from 'react';
import { useParams } from 'react-router-dom/cjs/react-router-dom.min';

const ProductDetail = () => {
    const id = useParams();
    return (
        <div>
            {`Product ${id}`}
        </div>
    );
};

export default ProductDetail;