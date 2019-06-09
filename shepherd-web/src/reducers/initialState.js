/**
 * Schema of store
 *
 * {
 *      clients: [
 *          {clientId: '1', clientName: 'sample client'},
 *          {clientId: '2', clientName: 'another client'}
 *      ],
 *
 *      endpoints: {
 *          [clientId-1]: [
 *              {
 *                  endpointId: 'endpointId',
 *                  endpointName: 'first endpoint',
 *                  daggraph: 'daggraph',
 *                  endpointDetails: 'endpointDetails'
 *              },
 *              {
 *                  endpointId: 'endpointId',
 *                  endpointName: 'first endpoint',
 *                  daggraph: 'daggraph',
 *                  endpointDetails: 'endpointDetails'
 *              }
 *          ]
 *      },
 *      executions: {
 *          [endpointId-1]: [
 *              {
 *                  executionsId: 'executionsId',
 *                  otherField: 'otherField'
 *              }
 *           ],
 *          [endpointId-2]: [
 *              {
 *                  executionsId: 'executionsId',
 *                  otherField: 'otherField'
 *              }
 *          ]
 *      },
 *      attempts: {
 *          [executionsId-1]: [
 *              {
 *                  attemptId: 'attemptId',
 *                  otherField: 'otherField',
 *                  flowState: 'IN-PROGRESS / KILLED / PROCESSED',
 *                  graph: {}
 *              }
 *          ]
 *          [executionsId-1]: [
 *              {
 *                  attemptId: 'attemptId',
 *                  otherField: 'otherField',
 *                  flowState: 'IN-PROGRESS / KILLED / PROCESSED',
 *                  graph: {}
 *              }
 *          ]
 *      }
 *
 * }
 *
 */

export default {
  clients: [],
  endpoints: {},
  isLoading: false
};
