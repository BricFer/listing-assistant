import { StrictMode } from 'react';
import { createRoot } from 'react-dom/client';
import { ListingAssistant } from './ListingAssistant';

import './index.css';

createRoot(document.getElementById('root')!).render(
  <StrictMode>
    <ListingAssistant />
  </StrictMode>,
)
