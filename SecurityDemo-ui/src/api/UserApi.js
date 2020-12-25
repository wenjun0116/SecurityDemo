import request from '@/utils/request'

export function registry(data) {
    return request({
        url: '/auth/registry',
        mothed: 'post',
        data
    })
}